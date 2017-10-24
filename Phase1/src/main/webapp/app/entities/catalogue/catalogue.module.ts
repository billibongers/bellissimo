import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BellisimoSharedModule } from '../../shared';
import {
    CatalogueService,
    CataloguePopupService,
    CatalogueComponent,
    CatalogueDetailComponent,
    CatalogueDialogComponent,
    CataloguePopupComponent,
    CatalogueDeletePopupComponent,
    CatalogueDeleteDialogComponent,
    catalogueRoute,
    cataloguePopupRoute,
} from './';

const ENTITY_STATES = [
    ...catalogueRoute,
    ...cataloguePopupRoute,
];

@NgModule({
    imports: [
        BellisimoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CatalogueComponent,
        CatalogueDetailComponent,
        CatalogueDialogComponent,
        CatalogueDeleteDialogComponent,
        CataloguePopupComponent,
        CatalogueDeletePopupComponent,
    ],
    entryComponents: [
        CatalogueComponent,
        CatalogueDialogComponent,
        CataloguePopupComponent,
        CatalogueDeleteDialogComponent,
        CatalogueDeletePopupComponent,
    ],
    providers: [
        CatalogueService,
        CataloguePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BellisimoCatalogueModule {}
