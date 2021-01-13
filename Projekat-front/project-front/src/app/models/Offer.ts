export class Offer {
    id: number;
    description: string;
    title: string;
    avgRating: number;
    nmbOfRatings: number;
    lon: number;
    lat: number;
    place: string;

}

// page of offers, izmijeniti
export class Page {
    pageNumber;
    pageSize;
    content: Offer[];

    constructor (pageNumber, pageSize, offers){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.content = offers;
    }
}