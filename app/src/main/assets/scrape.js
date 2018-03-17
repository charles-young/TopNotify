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
            document.write(res);
            return;
        }
        window.setTimeout(function() {
            checkForChanges();
        }, 25);
    }
}