
CKEDITOR.addTemplates("default", {
    imagesPath: CKEDITOR.getUrl(CKEDITOR.plugins.getPath("templates") + "templates/images/"),
    hiddenElement:function hideElement() {
    //document.getElementById(aa.id).style.display = "none";
        return 3+5;
},
    templates: [{
        title: "Image and Title",
        image: "template1.gif",
        description: "One ",
        html: '<h1><span id="title"  style="display: inline">请输入标题....</span></h1><hr><br>'
    },
        ]
});

function hideElement(a,b) {
    alert(a+b);
    //document.getElementById(aa.id).style.display = "none";
}
