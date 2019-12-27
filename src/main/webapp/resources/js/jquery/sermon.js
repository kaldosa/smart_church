(function (factory) {
if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module.
    define(['jquery', './fineuploader/fileuploader'], factory);
} else {
    // Browser globals
    factory(window.jQuery,
    		window.fileuploader);
}
}(function ($) {

	defaults = {
			debug: true,
		multiple: false,
		autoUpload: true,
		disableDefaultDropzone: true,
		forceMultipart: true,
		inputName: 'file',
		uploadButtonText: '업로드할 파일을 선택해 주세요.',
		template: '<div class="qq-uploader span12">' +
		'<div class="qq-upload-button btn btn-success" style="width: auto;"><i class="icon-upload icon-white"></i> {uploadButtonText}</div>' +
		'<ul class="qq-upload-list" style="margin-top: 10px; text-align: center;"></ul>' +
		'</div>',
		classes: {
			button: 'qq-upload-button',
			list: 'qq-upload-list',
			progressBar: 'qq-progress-bar',
			file: 'qq-upload-file',
			spinner: 'qq-upload-spinner',
			finished: 'qq-upload-finished',
			size: 'qq-upload-size',
			cancel: 'qq-upload-cancel',
			failText: 'qq-upload-failed-text',
			success: 'alert alert-success',
			fail: 'alert alert-error',
			successIcon: null,
			failIcon: null
		},
		failedUploadTextDisplay: {
			mode: 'custom',
			maxChars: 50,
			responseProperty: 'error',
			enableTooltip: true
		}
	};
	
	$.fn.uploader_init = function(options) {
		
		$.extend(defaults, options);

		return new qq.FileUploader(defaults);
	};

}));
	