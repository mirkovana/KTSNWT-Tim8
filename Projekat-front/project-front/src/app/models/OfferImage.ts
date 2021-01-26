export class OfferImage {
    id: number;
    description: string; 
    imageBase64: string;

    constructor(id?:number, desc?:string, image?:string){
        this.id = id;
        this.description = desc;
        this.imageBase64 = image;
    }

    
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