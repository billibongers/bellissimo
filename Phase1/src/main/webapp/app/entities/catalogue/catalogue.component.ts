import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Catalogue } from './catalogue.model';
import { CatalogueService } from './catalogue.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-catalogue',
    templateUrl: './catalogue.component.html'
})
export class CatalogueComponent implements OnInit, OnDestroy {
catalogues: Catalogue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private catalogueService: CatalogueService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.catalogueService.query().subscribe(
            (res: ResponseWrapper) => {
                this.catalogues = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCatalogues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Catalogue) {
        return item.id;
    }
    registerChangeInCatalogues() {
        this.eventSubscriber = this.eventManager.subscribe('catalogueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
