/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 ckeditor_4.3.4_full.zip
 */

CKEDITOR.plugins.addExternal('fmath_formula', 'plugins/fmath_formula/', 'plugin.js','html5video','video');



CKEDITOR.editorConfig = function (config) {
    //config.filebrowserBrowseUrl =  '${base}/ckfinder/ckfinder.html' ;
    //config.filebrowserImageBrowseUrl =  '${base}/ckfinder/ckfinder.html?type=Images' ;
    //config.filebrowserFlashBrowseUrl =  '${base}/ckfinder/ckfinder.html?type=Flash' ;
    //config.filebrowserUploadUrl = "/edit/ckeditorUpload";
    config.filebrowserUploadUrl = "/ckeditor/imageUpload";
    //config.filebrowserUploadUrl =  '${base}/ckEditorAjax_uploadFile.action';
    // config.filebrowserImageUploadUrl = '${base}/ckEditorAjax_uploadImg.action';
    //config.removeDialogTabs = 'image:advanced;image:Link';//隐藏超链接与高级选项
    config.filebrowserImageUploadUrl = "/ckeditor/imageUpload";//上传图片的地址
    config.filebrowserHtml5videoUploadUrl = "/ckeditor/videoUpload";//上传视频的地址
    config.image_previewText = ' '//设置图片预览默认值为' '


    config.filebrowserWindowWidth = '1000';
    config.filebrowserWindowHeight = '700';
    config.language = "zh-cn";
    config.toolbar = 'Full';
    config.fullPage=true;

    config.toolbar_MyToolbar =
        [
            {name: 'document', items: ['Source', 'Preview']},
            {name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']},
            {name: 'basicstyles', items: ['Bold', 'Italic', 'Strike', '-', 'RemoveFormat']}
        ];

    config.toolbar_Full =
        [
            ['Source', '-', 'Save', 'NewPage', 'Preview', '-', 'Templates'],
            ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Print', 'SpellChecker', 'Scayt'],
            ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'],
            ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
            '/',
            ['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript'],
            ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote'],
            ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
            ['Link', 'Unlink', 'Anchor', 'fmath_formula'],
            ['Image', 'Flash', 'Video','Html5video','Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak'],
            //['Image','Flash','FlvPlayer','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
            '/',
            ['Styles', 'Format', 'Font', 'FontSize'],
            ['TextColor', 'BGColor'],
            ['Maximize', 'ShowBlocks', '-', 'About']
        ];
    //config.extraPlugins = 'flvPlayer,fmath_formula';
    config.extraPlugins = 'confighelper,html5video';
    //config.placeholder = '<h1>请输入标题...<h1>'+'<hr><br>';
    //config.uiColor='Kama'

}; 

