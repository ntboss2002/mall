// 搜索信息，展开，收起
$('legend').click(function () {
    $('fieldset div').slideToggle();
});

// 头像展示
$('body').on('click', '.avatar-show', function () {
    var url = $(this).attr('src');
    if(!!url){
        var r = '';
        r += '<div style="width: 100%; height: 100%; position: relative;">';
        r += '<img src="'+url+'" style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; max-height: 100%; max-width: 100%; margin: auto;">';
        r += '</div>';
        layer.open({
            type: 1,
            title: '图片预览',
            area: ['400px', '600px'],
            content: r,
            shadeClose: true
        });
    }
});