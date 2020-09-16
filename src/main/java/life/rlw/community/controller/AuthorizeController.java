package life.rlw.community.controller;

import life.rlw.community.dto.AccessTokenDTO;
import life.rlw.community.dto.GithubUser;
import life.rlw.community.model.User;
import life.rlw.community.provider.GithubProvider;
import life.rlw.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    //将配置封装到application里
    @Value("${github.client.id}")
    private String gitclientId;
    @Value("${github.client.secret}")
    private String gitclientSecret;
    @Value("${github.redirect.uri}")
    private String gitredirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/githubcallback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(gitclientId);
        accessTokenDTO.setClient_secret(gitclientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(gitredirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null && githubUser.getId()!=null){
            User user=new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";//重定向
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }

    }


        @GetMapping("/logout")
        private String logout(HttpServletRequest request,
                              HttpServletResponse response){
            request.getSession().removeAttribute("user");
            Cookie cookie=new Cookie("token",null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "redirect:/";
        }
    @GetMapping("/login")
    private String login(){
        return "login";
    }
}
