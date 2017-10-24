/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BellisimoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CatalogueDetailComponent } from '../../../../../../main/webapp/app/entities/catalogue/catalogue-detail.component';
import { CatalogueService } from '../../../../../../main/webapp/app/entities/catalogue/catalogue.service';
import { Catalogue } from '../../../../../../main/webapp/app/entities/catalogue/catalogue.model';

describe('Component Tests', () => {

    describe('Catalogue Management Detail Component', () => {
        let comp: CatalogueDetailComponent;
        let fixture: ComponentFixture<CatalogueDetailComponent>;
        let service: CatalogueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BellisimoTestModule],
                declarations: [CatalogueDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CatalogueService,
                    JhiEventManager
                ]
            }).overrideTemplate(CatalogueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CatalogueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CatalogueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Catalogue(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.catalogue).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
