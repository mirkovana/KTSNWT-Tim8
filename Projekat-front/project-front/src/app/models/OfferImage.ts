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