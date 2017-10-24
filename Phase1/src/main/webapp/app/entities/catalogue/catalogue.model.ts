import { BaseEntity } from './../../shared';

export class Catalogue implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public price?: number,
        public onspecial?: boolean,
        public specialPrice?: number,
        public path?: string,
        public valid?: boolean,
    ) {
        this.onspecial = false;
        this.valid = false;
    }
}
