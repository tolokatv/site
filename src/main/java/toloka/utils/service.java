package toloka.utils;

import org.springframework.beans.factory.annotation.Value;
import toloka.model.security.SiteUser;

/**
 * тут будуть вселякі корисні утілити
 */
public class service {

//    # dir for tmp files
//    site.tmp.directory=/worker/tmp/
//            # dir for user video file
//    site.rot.directory=/worker/rot/
//            # dir for studia video files
//    site.studio.directory=/worker/studia/
//            # dir for playlist
//    site.playlist.directory=/worker/playlist/
//            # dir for user file
//    site.user.directory=/worker/user/

    //Save the uploaded file to this folder
    @Value("${site.tmp.directory}")
    private String TMP_FOLDER;
    @Value("${site.rot.directory}")
    private String ROT_FOLDER;
    @Value("${site.studio.directory}")
    private String STUDIO_FOLDER;
    @Value("${site.playlist.directory}")
    private String PLAYLIST_FOLDER;
    @Value("${site.user.directory}")
    private String USER_FOLDER;

// отримаємо дерикторії проекту - що де сберігається
    public String get_site_directory(String Directory_mode, SiteUser user)
    {
        String ret = "";
        String IDUser = "";

        if (user != null) {
            // отримуємо ID користувача в строку
             IDUser = user.getId().toString();
        }
        if (Directory_mode.equals("ROT") ) // каталог для ROT
        {
            if (IDUser != null) {
                ret = ROT_FOLDER + IDUser + "/";
            }
        } else if (Directory_mode.equals("TMP")) //
        {
            ret = TMP_FOLDER;
        } else if (Directory_mode.equals("STUDIO")) {
            ret = STUDIO_FOLDER;
        } else if (Directory_mode.equals("PLAYLIST")) {
            ret = PLAYLIST_FOLDER;
        } else if (Directory_mode.equals("USER")) {
            ret = USER_FOLDER;
        }
        return ret;
    }

    // перевіряємо права користувача
    public static boolean check_user_privelidge (char Mode, String Obj, SiteUser User){
        // TODO написать проверку прав пользователя
        // где проверяем: ROT, загрузка, удаление, блог, сайт
        // что проверяем: запись, загрузку, удаление, редактирование
        //                W       L         D         E
        System.out.println("===== toloka.utils.service ");
        System.out.println("===== Mode "+Mode+" Object "+Obj+" User "+User.getFirstName() );
        

        return true;
    }
}
