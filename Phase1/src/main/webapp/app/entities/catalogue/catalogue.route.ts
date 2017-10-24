import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CatalogueComponent } from './catalogue.component';
import { CatalogueDetailComponent } from './catalogue-detail.component';
import { CataloguePopupComponent } from './catalogue-dialog.component';
import { CatalogueDeletePopupComponent } from './catalogue-delete-dialog.component';

export const catalogueRoute: Routes = [
    {
        path: 'catalogue',
        component: CatalogueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Catalogues'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'catalogue/:id',
        component: CatalogueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Catalogues'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cataloguePopupRoute: Routes = [
    {
        path: 'catalogue-new',
        component: CataloguePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Catalogues'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'catalogue/:id/edit',
        component: CataloguePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Catalogues'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'catalogue/:id/delete',
        component: CatalogueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Catalogues'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
