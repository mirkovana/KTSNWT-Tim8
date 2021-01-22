export class OfferImage {
    ID: number;
    description: string;
    imageBase64: string;
}


export class OfferImagePage {
    pageNumber;
    pageSize;
    content: OfferImage[];

    constructor(pageNumber, pageSize, offerImages) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.content = offerImages;
    }
}

export class Page1 {
    content: OfferImage[];
    totalElements: number;
    totalPages: number;

    constructor(content, totalEl, totalP){
        this.content = content;
        this.totalElements = totalEl;
        this.totalPages = totalP;
    }

      
}