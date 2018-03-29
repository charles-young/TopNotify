function checkForChanges () {
    if (window.location.href == 'https://app.tophat.com/emobile/') {
        var elemObserver = document.getElementsByClassName('card_subtitle');
        if (elemObserver.length > 0) {
            var res = '';
            var i;
            for (i = 0; i < document.querySelectorAll('span.card_subtitle:nth-child(3)').length; i++) {
                res += document.querySelectorAll('span.card_subtitle:nth-child(3)')[i].innerHTML.slice(11, 17);
                Android.storeClassIndex(i);
                Android.storeClassID("Course" + i, document.querySelectorAll('span.card_subtitle:nth-child(3)')[i].innerHTML.slice(11, 17))
            }
            Android.storeClassIndex(i);
            Android.startQuestionService();
            return;
        }
        window.setTimeout(function() {
            checkForChanges();
        }, 25);
    }
    /*if (window.location.href.match(/\/emobile\/(\d+)\g)) {
        var origOpen = XMLHttpRequest.prototype.open;
        XMLHttpRequest.prototype.open = function() {
            this.addEventListener('load', function() {
                console.log(this. responseURL);
                if (this.responseURL.match(/\/api\/v3\/course\/\s*(\d+)\s*\/student_viewable_module_item\//g)) {
                    Android.storeClassToken(window.location.href.match(/(\d+)\g)), this.responseURL.match(/(\d+)\g)));
                }
            });
            origOpen.apply(this, arguments);
        };
    }*/
}