package com.rfb.repository;

import org.springframework.beans.factory.annotation.Autowired;

abstract class AbstractRepositoryTest {

    @Autowired
    RfbLocationRepository rfbLocationRepository;

    @Autowired
    RfbEventRepository rfbEventRepository;

    @Autowired
    RfbEventAttendanceRepository rfbEventAttendanceRepository;

    @Autowired
    RfbUserRepository rfbUserRepository;
}
