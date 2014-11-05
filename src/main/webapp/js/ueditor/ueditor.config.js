(function () {
    var URL = window.UEDITOR_HOME_URL || getUEBasePath();
    window.UEDITOR_CONFIG = {UEDITOR_HOME_URL: URL, serverUrl: URL + "config", toolbars: [
        ["source", "|", "undo", "redo", "|", "bold", "italic", "underline", "strikethrough", "blockquote", "|", "fontsize", "forecolor", "backcolor", "|", "indent", "justifyleft", "justifycenter", "justifyright", "justifyjustify", "|", "insertorderedlist", "insertunorderedlist", "|", "simpleupload", "insertimage"]
    ], 'insertorderedlist': {
        //      //自定的样式
        //        'num':'1,2,3...',
        //        'num1':'1),2),3)...',
        //        'num2':'(1),(2),(3)...',
        //        'cn':'一,二,三....',
        //        'cn1':'一),二),三)....',
        //        'cn2':'(一),(二),(三)....',
        //     //系统自带
        'decimal': '',         //'1,2,3...'
        'lower-alpha': '',    // 'a,b,c...'
        'lower-roman': '',    //'i,ii,iii...'
        'upper-alpha': '', //lang   //'A,B,C'
        'upper-roman': ''      //'I,II,III...'
    }

        //insertunorderedlist
        //无序列表的下拉配置，值留空时支持多语言自动识别，若配置值，则以此值为准
        , insertunorderedlist: { //自定的样式
            //    'dash' :'— 破折号', //-破折号
            //    'dot':' 。 小圆圈', //系统自带
            'circle': '',  // '○ 小圆圈'
            'disc': '',    // '● 小圆点'
            'square': ''   //'■ 小方块'
        }, elementPathEnabled: false, wordCount: false
    };
    function getUEBasePath(docUrl, confUrl) {
        return getBasePath(docUrl || self.document.URL || self.location.href, confUrl || getConfigFilePath())
    }

    function getConfigFilePath() {
        var configPath = document.getElementsByTagName("script");
        return configPath[configPath.length - 1].src
    }

    function getBasePath(docUrl, confUrl) {
        var basePath = confUrl;
        if (/^(\/|\\\\)/.test(confUrl)) {
            basePath = /^.+?\w(\/|\\\\)/.exec(docUrl)[0] + confUrl.replace(/^(\/|\\\\)/, "")
        } else {
            if (!/^[a-z]+:/i.test(confUrl)) {
                docUrl = docUrl.split("#")[0].split("?")[0].replace(/[^\\\/]+$/, "");
                basePath = docUrl + "" + confUrl
            }
        }
        return optimizationPath(basePath)
    }

    function optimizationPath(path) {
        var protocol = /^[a-z]+:\/\//.exec(path)[0], tmp = null, res = [];
        path = path.replace(protocol, "").split("?")[0].split("#")[0];
        path = path.replace(/\\/g, "/").split(/\//);
        path[path.length - 1] = "";
        while (path.length) {
            if ((tmp = path.shift()) === "..") {
                res.pop()
            } else {
                if (tmp !== ".") {
                    res.push(tmp)
                }
            }
        }
        return protocol + res.join("/")
    }

    window.UE = {getUEBasePath: getUEBasePath}
})();