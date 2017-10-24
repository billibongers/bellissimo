import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Catalogue } from './catalogue.model';
import { CataloguePopupService } from './catalogue-popup.service';
import { CatalogueService } from './catalogue.service';

@Component({
    selector: 'jhi-catalogue-dialog',
    templateUrl: './catalogue-dialog.component.html'
})
export class CatalogueDialogComponent implements OnInit {

    catalogue: Catalogue;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private catalogueService: CatalogueService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.catalogue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.catalogueService.update(this.catalogue));
        } else {
            this.subscribeToSaveResponse(
                this.catalogueService.create(this.catalogue));
        }
    }

    private subscribeToSaveResponse(result: Observable<Catalogue>) {
        result.subscribe((res: Catalogue) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Catalogue) {
        this.eventManager.broadcast({ name: 'catalogueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-catalogue-popup',
    template: ''
})
export class CataloguePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cataloguePopupService: CataloguePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cataloguePopupService
                    .open(CatalogueDialogComponent as Component, params['id']);
            } else {
                this.cataloguePopupService
                    .open(CatalogueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
