var seckill={

    URL:{
        now: function(){
            return '/seckill/time/now';
        },
        exposer:function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution:function (seckillId,md5) {
            return '/seckill/' +seckillId+'/'+md5+'/execution'
        }
    },

    handleSeckillkill:function(seckillId,node){
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">Grab it now</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            if (result && result['success']){
                var exposer = result['data'];
                console.log(exposer)
                if (exposer['exposed']) {
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log("killUrl : " + killUrl)
                    $('#killBtn').one('click',function () {
                        $(this).addClass('disable');
                        $.post(killUrl,{},function (result) {
                            console.log('result:'+result);
                            if (result && result['success']){
                                var killResult = result['data'];
                                console.log(killResult);
                                var state = killResult['status'];
                                var statusInfo = killResult['statusInfo'];
                                node.html('<span class="label label-success">' + statusInfo + '</span>')

                            }
                        });
                    });
                    node.show();
                }else {
                    var now = exposer['now'];
                    var start = exposer['strat'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId,now,start,end);
                }
            }else {
                console.log('result:' + result);
            }
        })
    },

    validatePhone: function(phone){
      if(phone && phone.length==10 && !isNaN(phone)){
          return true;
      }else{
          return false;
      }
    },
    countDown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            seckillBox.html('Seckill End');
        }else if (nowTime < startTime){
            var killTime = new Date(startTime);

            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('Countdown：%D Days %H Hours %M Minutes %S Seconds');
                seckillBox.html(format)
            }).on('finish.countdown',function () {
                seckill.handleSeckillkill(seckillId,seckillBox);
            });
        } else {
            seckill.handleSeckillkill(seckillId,seckillBox);
        }
    },
    detail: {
        init: function (params) {
            var killPhone = $.cookie('killPhone');

            if (!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({

                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log(inputPhone);
                    if (seckill.validatePhone(inputPhone)) {

                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">Invalid phone number！</label>').show(300);
                    }
                });
            }

            var seckillId=params['seckillId'];
            var startTime=params['startTime'];
            var endTime=params['endTime'];
            $.get(seckill.URL.now(), {}, function (result) {
                if(result && result['success']){
                    var nowTime=result['data'];
                    seckill.countDown(seckillId, nowTime, startTime ,endTime);
                }else{
                    console.log('result:'+result);
                }
            })
        }
    }
}