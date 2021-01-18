export class PaginatorPageable {
    previousPageIndex; 
    pageIndex; 
    pageSize; 
    length;

    constructor(previousPageIndex, pageIndex, pageSize, lenght){
        this.previousPageIndex = previousPageIndex;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.length = lenght;
    }
}