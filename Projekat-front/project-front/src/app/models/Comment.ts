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

    constructor(id, text, imageBase64, imageFile, username, date, canEdit, editing, slika){
        this.id = id;
        this.text = text;
        this.imageBase64 = imageBase64;
        this.imageFile = imageFile;
        this.username = username;
        this.date = date;
        this.username = username;
        this.canEdit = canEdit;
        this.editing = editing;
        this.slika = slika;
    }

}