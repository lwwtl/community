
/**
* 提交回复*/
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if(content==""){
        alert("输入内容不能为空！");
        return;
    }
    $.ajax({
        type:"POST",
        url: "/comment",
        contentType:"application/json",
        dateType:"json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success:function (response) {
                if(response.code==200){
                    window.location.reload();
                }else{
                    if(response.code==2003){
                        var isAccepted = confirm(response.message);
                        if(isAccepted){
                            window.open("https://github.com/login/oauth/authorize?client_id=Iv1.923a425f456712c6&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                            window.localStorage.setItem("closable",true);
                        }
                    }else
                    {
                        alert(response.message);
                    }
                }

            console.log(response);
        }
    });
}
/**
 * 二级评论*/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    //获取展开状态
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        //展开二级评论
        comments.addClass("in");
        //标记二级评论的展开状态
        e.setAttribute("data-collapse","in");
        e.classList.add("active");

    }

}