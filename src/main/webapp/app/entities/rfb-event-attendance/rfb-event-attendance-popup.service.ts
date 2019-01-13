import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { RfbEventAttendance } from './rfb-event-attendance.model';
import { RfbEventAttendanceService } from './rfb-event-attendance.service';

@Injectable()
export class RfbEventAttendancePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private rfbEventAttendanceService: RfbEventAttendanceService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rfbEventAttendanceService.find(id)
                    .subscribe((rfbEventAttendanceResponse: HttpResponse<RfbEventAttendance>) => {
                        const rfbEventAttendance: RfbEventAttendance = rfbEventAttendanceResponse.body;
                        if (rfbEventAttendance.attedanceDate) {
                            rfbEventAttendance.attedanceDate = {
                                year: rfbEventAttendance.attedanceDate.getFullYear(),
                                month: rfbEventAttendance.attedanceDate.getMonth() + 1,
                                day: rfbEventAttendance.attedanceDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.rfbEventAttendanceModalRef(component, rfbEventAttendance);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rfbEventAttendanceModalRef(component, new RfbEventAttendance());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rfbEventAttendanceModalRef(component: Component, rfbEventAttendance: RfbEventAttendance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rfbEventAttendance = rfbEventAttendance;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
