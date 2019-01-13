package com.rfb.service;

import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbLocation;
import com.rfb.repository.RfbEventRepository;
import com.rfb.repository.RfbLocationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RfbEventCodeService {

    private final Logger log = LoggerFactory.getLogger(RfbEventCodeService.class);

    private final RfbLocationRepository rfbLocationRepository;
    private final RfbEventRepository rfbEventRepository;

    public RfbEventCodeService(RfbLocationRepository rfbLocationRepository, RfbEventRepository rfbEventRepository) {
        this.rfbLocationRepository = rfbLocationRepository;
        this.rfbEventRepository = rfbEventRepository;
    }

    @Scheduled(cron = "0 0 * * * ?") //run once per hour, at top of hour
//    @Scheduled(cron = "0 * * * * ?") //run once per min
//    @Scheduled(cron = "* * * * * ?") //run once per sec
    public void generateRunEventCodes() {
        log.debug("Generating events");

        List<RfbLocation> rfbLocations = rfbLocationRepository.findAllByRunDayOfWeek(LocalDate.now().getDayOfWeek().getValue());

        log.debug("Locations found for Events: " + rfbLocations.size());

        rfbLocations.forEach(rfbLocation -> {
            log.debug("Checking Events for location: " + rfbLocation.getId());

            RfbEvent existingEvent = rfbEventRepository.findByRfbLocationAndEventDate(rfbLocation, LocalDate.now());

            if (existingEvent == null) {
                log.debug("Event not found, Creating Event");

                RfbEvent newEvent = new RfbEvent();
                newEvent.setRfbLocation(rfbLocation);
                newEvent.setEventDate(LocalDate.now());
                newEvent.setEventCode(RandomStringUtils.randomAlphanumeric(10).toUpperCase());

                rfbEventRepository.save(newEvent);

                log.debug("Event Created: "  + newEvent.toString());
            } else {
                log.debug("Event exists for day");
            }
        });
    }
}
