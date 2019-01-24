import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import {Account, AccountService, LoginModalService, Principal, User} from '../shared';
import {RfbLocation, RfbLocationService} from '../entities/rfb-location';
import {EntityResponseType, RfbEvent, RfbEventService} from '../entities/rfb-event';
import {RfbEventAttendance, RfbEventAttendanceService} from '../entities/rfb-event-attendance';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    isSaving: boolean;
    locations: RfbLocation[];
    todaysEvent: RfbEvent;
    currentUser: User;
    model: any;
    rfbEventAttendance: RfbEventAttendance;
    errors: any = {invalidEventCode: ''};
    checkedIn = false;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private locationService: RfbLocationService,
        private eventService: RfbEventService,
        private accountService: AccountService,
        private rfbEventAttendanceService: RfbEventAttendanceService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.loadLocations();
        this.model = {location: 0, eventCode: ''};
        this.rfbEventAttendance = new RfbEventAttendance(null, new Date(), new RfbEvent(), new User());

        this.accountService.get().subscribe((user: HttpResponse<User>) => {
            this.currentUser = user.body;
            this.rfbEventAttendance.userDTO = user.body;

            if (this.currentUser.authorities.indexOf('ROLE_ORGANIZER') !== -1) {
                this.setTodayEvent(this.currentUser.homeLocation);
            }

            if (this.currentUser.authorities.indexOf('ROLE_RUNNER') !== -1) {
                this.model.location = this.currentUser.homeLocation;
            }
        });
    }

    setTodayEvent(locationID: number) {
        this.eventService.findByLocation(locationID).subscribe((rfbEvent: EntityResponseType) => {
            this.todaysEvent = rfbEvent.body;
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    loadLocations() {
        this.locationService.query({
           page: 0,
           size: 100,
           sort: ['locationName', 'ASC']
        }).subscribe((res: HttpResponse<RfbLocation[]>) => {
            this.locations = res.body;
        }, (res: HttpErrorResponse) => { console.log(res); });
    }

    checkIn() {
        this.eventService.findByLocation(this.model.location).subscribe((rfbEvent: EntityResponseType) => {
            const thisEvent = rfbEvent.body;
            this.rfbEventAttendance.rfbEventDTO = rfbEvent.body;
            if (thisEvent.eventCode === this.model.eventCode) {
                this.rfbEventAttendanceService.create(this.rfbEventAttendance).subscribe((userCheckedIn: EntityResponseType) => {
                   this.checkedIn = true;
                });
            } else {
                this.errors.invalidEventCode = "There is either no run today for this location or you have entered an incorrect event code. Please try again.";
            }
        });
    }

    clear() {}

    setHomeLocation() {}
}
