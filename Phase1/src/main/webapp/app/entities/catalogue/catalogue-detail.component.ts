import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Catalogue } from './catalogue.model';
import { CatalogueService } from './catalogue.service';

@Component({
    selector: 'jhi-catalogue-detail',
    templateUrl: './catalogue-detail.component.html'
})
export class CatalogueDetailComponent implements OnInit, OnDestroy {

    catalogue: Catalogue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private catalogueService: CatalogueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCatalogues();
    }

    load(id) {
        this.catalogueService.find(id).subscribe((catalogue) => {
            this.catalogue = catalogue;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCatalogues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'catalogueListModification',
            (response) => this.load(this.catalogue.id)
        );
    }
}
