var user = {
  init : function () {
    var _this = this;
    $('#btn-save').on('click', function () {
      _this.save();
    });

  },

  save : function () {
    var data = {
      name: $('#user_name').val(),
      password: $('#password').val(),
      confirm_password: $('#confirm_password').val(),
    };

    var reg = /^[a-zA-Z0-9]+$/;

    if(!reg.test(data.name)){
      alert("아이디를 영숫자로만 작성해주세요.");
      return false;
    }
    if(data.name.length < 2){
      alert("2자 이상으로 해주세요.");
      return false;
    }
    if(data.password !== data.confirm_password){
      alert("비밀번호가 일치하지 않습니다.");
      return false;
    }

    $.ajax({
      type: 'POST',
      url: '/user/signup',
      dataType: 'json',
      contentType:'application/json; charset=utf-8',
      data: JSON.stringify(data)

    }).done(function() {
      alert('회원 가입이 완료되었습니다.');
      window.location.href = '/main';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  }

};

user.init();