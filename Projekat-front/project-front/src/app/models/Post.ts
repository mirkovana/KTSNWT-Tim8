export class Post {
    id: number;
    content: string;
    title: string;
    date: string;

   
}

// page of offers, izmijeniti
export class Page {
    content: Post[];
    totalElements: number;
    totalPages: number;

    constructor(content, totalEl, totalP){
        this.content = content;
        this.totalElements = totalEl;
        this.totalPages = totalP;
    }

      
}