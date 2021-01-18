export class Offer {
    id: number;
    description: string;
    title: string;
    avgRating: number;
    nmbOfRatings: number;
    lon: number;
    lat: number;
    place: string;

    public toString():string{
        return "ID: " + this.id +", " + "title: " + this.title;
    }

}

// page of offers, izmijeniti
export class Page {
    content: Offer[];
    totalElements: number;
    totalPages: number;

    constructor(content, totalEl, totalP){
        this.content = content;
        this.totalElements = totalEl;
        this.totalPages = totalP;
    }

      
}

export class PageGen {
    content: any;
    totalElements: number;
    totalPages: number;

    constructor(content, totalEl, totalP){
        this.content = content;
        this.totalElements = totalEl;
        this.totalPages = totalP;
    }

      
}

/*export class Page {
    pageNumber;
    pageSize;
    content: Offer[];

    constructor (pageNumber, pageSize, offers){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.content = offers;
    }

    
}*/