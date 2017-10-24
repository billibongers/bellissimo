import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Catalogue } from './catalogue.model';
import { CataloguePopupService } from './catalogue-popup.service';
import { CatalogueService } from './catalogue.service';

@Component({
    selector: 'jhi-catalogue-delete-dialog',
    templateUrl: './catalogue-delete-dialog.component.html'
})
export class CatalogueDeleteDialogComponent {

    catalogue: Catalogue;

    constructor(
        private catalogueService: CatalogueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.catalogueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'catalogueListModification',
                content: 'Deleted an catalogue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-catalogue-delete-popup',
    template: ''
})
export class CatalogueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cataloguePopupService: CataloguePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cataloguePopupService
                .open(CatalogueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
