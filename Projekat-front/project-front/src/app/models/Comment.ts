export class Comment {
    id?;
    text: string;
    imageBase64: string;
    imageFile?: File;
    username?: string;
    date?
    canEdit?
    public editing?
    slika

    constructor(id, text, imageBase64, imagFile, username, date, canEdit, editing, slika){
        this.id = id;
        this.text = text;
    }

}