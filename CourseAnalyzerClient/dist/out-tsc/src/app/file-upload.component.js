var FileUplaod = /** @class */ (function () {
    function FileUplaod() {
        this.fileToUpload = null;
    }
    FileUplaod.prototype.handleFileInput = function (files) {
        this.fileToUpload = files.item(0);
    };
    return FileUplaod;
}());
//# sourceMappingURL=file-upload.component.js.map