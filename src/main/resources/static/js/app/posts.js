var posts = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },

      getCookie : function (name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(';').shift();
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            headers: { "Authorization": 'Bearer ' + this.getCookie('access_token')
            },
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)

        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/main';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();
        var user_name = $('#user_name').val();

        if(user_name !== data.author){
            alert("본인 글만 수정할 수 있습니다.")
            return false;
        }

        $.ajax({
            type: 'PUT',
            headers: { "Authorization": 'Bearer ' + this.getCookie('access_token')
            },
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/main';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();
        var user_name = $('#user_name').val();
        var author = $('#author').val();

        if(user_name !== author && user_name !== 'admin'){
            alert("본인 글만 삭제할 수 있습니다.")
            return false;
        }

        $.ajax({
            type: 'DELETE',
            headers: { "Authorization": 'Bearer ' + this.getCookie('access_token')
            },
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/main';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

posts.init();
console.log("access_token is " + posts.getCookie('access_token'))