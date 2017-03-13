package techkids.cuong.myapplication.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Cuong on 1/5/2017.
 */
public class BoardGame extends RealmObject implements Serializable {

    public static String BOARD_GAME = "boardgame";

    @PrimaryKey
    private String id;

    private String name;

    private String imageUrl;

    private String detailUrl;

    private String rulesUrl;

    private String thumbUrl;

    private int minPlayer;

    private int maxPlayer;

    private String favoritePlayer;

    private int playingTime;

    private RealmList<RealmString> categories;

    private RealmList<RealmString> playType;

    private String rules;

    private String description;
    private Publisher publisher;

    private AppCategory appCategory;


    public BoardGame() {
    }

    public BoardGame(String id,String name, String imageUrl, String detailUrl, String rulesUrl, String thumbUrl, int minPlayer, int maxPlayer, String favoritePlayer, int playingTime, RealmString[] categories, RealmString[] playType, String rules, String description, Publisher publisher,AppCategory appCategory) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.detailUrl = detailUrl;
        this.rulesUrl = rulesUrl;
        this.thumbUrl = thumbUrl;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.favoritePlayer = favoritePlayer;
        this.playingTime = playingTime;
        this.categories = new RealmList<>();
        this.categories.addAll(Arrays.asList(categories));
        this.playType = new RealmList<>();
        this.playType.addAll(Arrays.asList(playType));
        this.description = description;
        this.rules = rules;
        this.publisher = publisher;
        this.appCategory = appCategory;
    }
    public BoardGame(String id,String name, String imageUrl, String detailUrl, String rulesUrl, String thumbUrl, int minPlayer, int maxPlayer, String favoritePlayer, int playingTime, String[] categories, String[] playType, String rules, String description, Publisher publisher,AppCategory appCategory) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.detailUrl = detailUrl;
        this.rulesUrl = rulesUrl;
        this.thumbUrl = thumbUrl;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.favoritePlayer = favoritePlayer;
        this.playingTime = playingTime;
        this.categories = new RealmList<>();
        for (String str : categories) {
            this.categories.add(new RealmString(str));
        }
        this.playType = new RealmList<>();
        for (String str : playType) {
            this.playType.add(new RealmString(str));
        }
        this.description = description;
        this.rules = rules;
        this.publisher = publisher;
        this.appCategory = appCategory;
    }

    @Override
    public String toString() {
        return "BoardGame{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public String getFavoritePlayer() {
        return favoritePlayer;
    }

    public int getPlayingTime() {
        return playingTime;
    }


    public ArrayList<String> createCategoryStringList() {
        ArrayList<String> stringList = new ArrayList<>();
        for (RealmString realmString : categories) {
            stringList.add(realmString.getValue());
        }

        return stringList;
    }


    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public String getRules() {
        return rules;
    }

    //    "https://cf.geekdo-images.com/images/pic2016054_md.jpg",
//            "https://view.publitas.com/p222-11815/coup/page/1",


    public String getId() {
        return id;
    }

    public String getRulesUrl() {
        return rulesUrl;
    }

    public ArrayList<String> createPlayTypeStringList() {
        ArrayList<String> stringList = new ArrayList<>();
        for (RealmString realmString : playType) {
            stringList.add(realmString.getValue());
        }

        return stringList;
    }

    public String getDescription() {
        return description;
    }

    public Publisher getPublisher() {
        //todo debugging
        if (publisher != null) {

            return publisher;
        } else {
            return new Publisher("null", null);
        }

    }



    public static BoardGame[] boardGameArray = {
            new BoardGame("000001","One Night Ultimate Werewolf",
                    "https://cf.geekdo-images.com/images/pic1809824.jpg",
                    "https://view.publitas.com/31715/238002/pdfs/29e027e72495889a166168ef7381e724e457f61b.pdf",
                    "https://view.publitas.com/31715/238026/pdfs/9a62cfa203a163ccae30cc9a02ca872e2321c5d6.pdf",
                    "https://cf.geekdo-images.com/images/pic1809823_t.jpg",
                    8, 18,
                    "11-15",
                    30,
                    new String[]{"buffing", "deduction", "horror", "murder", "mystery", "party"},
                    new String[]{"partnership", "player elimination", "role playing", "voting"},

                    "##Trình tự game\n" +
                            "\n" +
                            "### Giai đoạn ban đêm: \n" +
                            "Mọi người nhắm mắt, Quản Trò gọi vai trò đặc biệt nào thì vai trò ấy mở mắt và thực hiện chức năng của mình trong-yên-lặng. Trình tự gọi của Quản Trò như sau:\n" +
                            "1. Ăn trộm (Chỉ đêm đầu tiên)\n" +
                            "2. Cupid (Chỉ đêm đầu tiên)\n" +
                            "3.  2 người yêu nhau (Chỉ đêm đầu tiên)\n" +
                            "4. Bảo vệ\n" +
                            "5. Sói\n" +
                            "6. Tiên Tri\n" +
                            "7. Phù Thủy\n" +
                            "8. Thổi sáo\n" +
                            "9. Những người bị thôi miên\n" +
                            "10. Già Làng (Chỉ đêm đầu tiên)\n" +
                            "11. Thợ Săn\n" +
                            "###Ban ngày:\n" +
                            "Quản trò ra hiệu mọi người mở mắt, thông báo những ai đã chết đêm qua. Sau đó, dân làng bình bầu treo cổ một người bị nghi ngờ là Ma Sói trong ban ngày (Có thể hoãn không treo). Nếu có 2 người cùng có số phiếu bầu treo như nhau thì không ai bị treo cả.\n" +
                            "#####Tiên tri\n" +
                            "![enter image description here](https://raw.githubusercontent.com/cuonghq104/Images/master/Card/seer.png)\n" +
                            "\n" +
                            "> Mỗi đêm, khi được gọi dậy Tiên Tri sẽ chỉ một người. Nếu người đó là Sói Quản trò sẽ gật đầu.\n" +
                            "\n" +
                            "#####Thợ săn\n" +
                            "![enter image description here](https://raw.githubusercontent.com/cuonghq104/Images/master/Card/hunter.png)\n" +
                            ">Khi thợ săn chết, dù là dưới bất kỳ hình thức nào cũng có thể chọn một người chơi khác và kéo hắn xuống “Tuyền đường” cùng Thợ Săn\n" +
                            "\n" +
                            "#####Phù thủy\n" +
                            "![enter image description here](https://raw.githubusercontent.com/cuonghq104/Images/master/Card/witch.png)\n" +
                            ">Phù Thủy có hai bình thuốc: Một bình dùng để cứu một người, còn một bình dùng để giết một người. Mỗi đêm, Quản trò khi gọi Phù Thủy dậy sẽ cho Phù Thủy biết người bị giết bởi Sói đêm đó, và Phù Thủy được quyền quyết định xem có cứu người ấy hay không. Sau đó, quản trò sẽ hỏi xem Phù Thủy có dùng bình giết giết ai hay không. Một khi đã dùng bình thì Phù Thủy sẽ mất đi chức năng tương ứng, tuy nhiên vẫn được gọi dậy mỗi đêm và biết ai chết. Lưu ý: Nếu đêm đó Bảo Vệ đã Bảo Vệ đúng người thì Quản trò sẽ lắc tay, ra dấu là không ai chết cả.\n" +
                            "\n" +
                            "\n" +
                            "#####Cupid\n" +
                            "![enter image description here](https://raw.githubusercontent.com/cuonghq104/Images/master/Card/cupid.png)\n" +
                            ">Đầu mỗi ván chơi, Cupid sẽ được gọi dậy và chọn ra hai người yêu nhau. Cupid sau đó nhắm mắt lại và hai người yêu nhau sẽ được Quản Trò gọi dậy để biết mặt và Vai Trò của nhau. Nếu hai người thuộc hai phe khác nhau (Sói vs Dân) thì họ thành phe thứ ba với nhiệm vụ là hai người cuối cùng sống sót.\n" +
                            "\n" +
                            "#####Ma Sói\n" +
                            "![enter\t image description here](https://raw.githubusercontent.com/cuonghq104/Images/master/Card/WereWolf.jpg)\n" +
                            ">Mỗi đêm thức dậy, các Sói sẽ biết mặt lẫn nhau và sẽ cùng thống nhất giết một người. Sói có quyền không giết, và cũng có quyền tự giết lẫn nhau.\n" +
                            "\n" +
                            "#####Dân làng\n" +
                            "![enter image description here](https://raw.githubusercontent.com/cuonghq104/Images/master/Card/villager.png)\n" +
                            ">Năng lực rất đơn giản: Chết khi bị giết. Ngoài ra còn có năng lực bỏ phiếu treo người khác\n" +
                            "\n" +
                            "\n" +
                            "##Luật chơi\n" +
                            "\n" +
                            "Cách chơi của trò chơi là mỗi người sẽ được nhận lần lượt 1 lá bài để biết chức năng của mình, sau đó sẽ được quản trò bảo đi ngủ trong từng đêm. Mỗi đêm, quản trò gọi từng chức năng đặc biệt thức dậy như sói, cupid, witch ... để thực hiện công việc của mình rồi tiếp tục đi ngủ. Sau khi gọi lên hết cách chức năng. Quản trò thông báo mọi người mở mắt dậy và sẽ cho biết đêm qua ai bị giết.\n" +
                            "\n" +
                            "Sáng đó, mọi người sẽ cùng ngồi thảo luận với nhau, và bằng cơ sở lý luận, lập luận hay troll nhau gì gì đó ... mà cùng nhau tìm ra và treo cổ những con sói tinh ma.\n" +
                            "\n" +
                            "Còn về phía phe sói, thì cùng bảo vệ nhau để ly gián dân làng giành được chiến thắng cuối cùng.\n" +
                            "\n" +
                            "Nếu một người bị giết hay loại khỏi trò chơi vì quy phạm nội quy game, quản trò sẽ không cho biết chức năng của họ cho đến khi kết thúc game.\n" +
                            "\n" +
                            "Thứ tự gọi mỗi nhân vật trong đêm, phải theo 1 thứ tự nhất định không được thay đổi. Nếu quản trò gọi thứ tự sai, đôi khi sẽ gây sự nhầm lẫn hoặc sai sót trong game.\n" +
                            "\n" +
                            "Người chơi đã bị loại khỏi game do bị giết hoặc vi phạm, phải tuyệt đối tuân thủ và tôn trọng những người chơi khác bằng cách giữ im lặng và theo dõi game tiếp tục diễn ra.\n" +
                            "\n" +
                            "Mỗi buổi sáng, dân làng có tối đa 2 phút để cùng thảo luận với nhau.\n" +
                            "\n" +
                            "Sau đó có 5 giây để chỉ tay về phía người mình tình nghi, nếu ai bị chỉ tay nhiều nhất sẽ có 1 phút để tự bào chữa cho mình.\n" +
                            "\n" +
                            "Trong quá trình 1 phút tự bào chữa của người bị tình nghi, những người còn lại không được phép lên tiếng, nếu lên tiếng sẽ bị loại khỏi game. Trường hợp lên tiếng khi được người tình nghi hỏi trong lúc đang bào chữa cho mình, người lên tiếng sẽ bị mất quyền bỏ phiếu. Nếu nói chuyện ngoài luồng và cãi nhau, người chơi bị loại khỏi game\n" +
                            "\n" +
                            "Hết 1 phút, những người có quyền bỏ phiếu sẽ biểu quyết xem có chấp nhận phần tự bào chữa của người bị tình nghi không. Nếu số phiếu chấp nhận nhiều hơn số phiếu không chấp nhận thì người đó được sống và tiếp tục trò chơi, ngược lại thì người đó sẽ chết (quản trò sẽ thu lại lá bài chức năng)\n" +
                            "\n" +
                            "Dù người chơi bị chết giữ chức năng quan trọng nào đó, quản trò vẫn không được tiết lộ nên mỗi đêm vẫn phải gọi hết tất cả các chức năng của trò chơi lên để đánh lạc hướng người chơi và làm cơ sở cho phe sói có thời gian giả dạng đánh lừa dân làng. Do đó, việc bạn có chức năng đặc biệt nào đó mà bạn bị giết rồi mà quản trò vẫn gọi chức năng đó là điều hoàn toàn bình thường, đừng tỏ ra thắc mắc mà hãy im lặng theo dõi đến khi kết thúc trò chơi.\n" +
                            "\n" +
                            "<iframe width=\"100%\" height=\"260\" src=\"https://www.youtube.com/embed/-4uDuH4xXLg\" frameborder=\"0\" allowfullscreen></iframe>" +
                            "",

                    "No moderator, no elimination, ten-minute games.\n" +
                            "\n" +
                            "One Night Ultimate Werewolf is a fast game for 3-10 players in which everyone gets a role: One of the dastardly Werewolves, the tricky Troublemaker, the helpful Seer, or one of a dozen different characters, each with a special ability. In the course of a single morning, your village will decide who is a werewolf...because all it takes is lynching one werewolf to win!\n" +
                            "\n" +
                            "Because One Night Ultimate Werewolf is so fast, fun, and engaging, you'll want to play it again and again, and no two games are ever the same.",
                    new Publisher("Asterion Press", "http://www.negoziogiochi.it/media/catalog/category/Asterion_400B1_1.jpg"),
                    new AppCategory(false,true)),
            new BoardGame("000002","Uno",
                    "http://boardgame.vn/uploads/u/boardgame.vn/product/2016/08/23/23/31/boa1471948306.JPG",
                    "https://view.publitas.com/31715/238002/pdfs/29e027e72495889a166168ef7381e724e457f61b.pdf",
                    "https://view.publitas.com/31715/238026/pdfs/9a62cfa203a163ccae30cc9a02ca872e2321c5d6.pdf",
                    "https://ubistatic9-a.akamaihd.net/ubicomstatic/en-US/global/game-info/Uno-game_info-Boxart-tablet-560x698_Tablet_259523.jpg",
                    2, 10,
                    "4-6",
                    30,
                    new String[]{"popular", "family", "children", "tactical"},
                    new String[]{"hand management"},
                    null,
                    "",
                    null,
                    new AppCategory(true,false)),
            new BoardGame("000003","Coup",
                    "http://www.pubmeeple.com/wp-content/uploads/Coup3.jpg",
                    "https://view.publitas.com/31715/237992/pdfs/c27b7e30e500a8b9f1b7259db53f6eb5b974e76a.pdf",
                    "https://view.publitas.com/31715/238019/pdfs/7249eafcc8b80beac36891431e3282f6803d9a0b.pdf",
                    "https://images-na.ssl-images-amazon.com/images/I/81sNAwxviTL._SL1500_.jpg",
                    2, 6,
                    "5",
                    15,
                    new String[]{"Bluffing", "Card", "Deduction"},
                    new String[]{"Memory", "Player Elimination", "Take that"},
                    null,
                    "",
                    null,
                    new AppCategory(true,false)),

            new BoardGame("000004","Shadow Hunters", "http://cf.geekdo-images.com/images/pic1215982.jpg", "",
                    "http://cf.geekdo-images.com/images/pic1215982_t.jpg",
                    "http://boardgame.vn/uploads/u/boardgame.vn/product/2015/10/23/05/49/cov1445532563.jpg",
                    4, 8,
                    "7-8",
                    45,
                    new String[]{"Adventure", "Buffing", "Card", "Deduction", "Horror", "Party Game"},
                    new String[]{"Dice Rolling", "Partnership", "Player Elimination"},
                    null,
                    "",
                    null,
                    new AppCategory(true,false)),
            new BoardGame("000005","Exploding Kittens", "", "", "", "https://pbs.twimg.com/profile_images/628974459550961664/ZN9DHHkH_reasonably_small.png",
                    2, 5,
                    "4-5",
                    20,
                    new String[]{"Card Game", "Humour", "Animal",},
                    new String[]{"Hand Management"},
                    null,
                    "",
                    null,
                    new AppCategory(true,false)
            ),
            new BoardGame("000006", "The Castle of Burgundy", "", "", "", "https://cf.geekdo-images.com/images/pic1176894_t.jpg",
                    2, 4,
                    "2",
                    60,
                    new String[] {"Dice Rolling", "Set Collection", "Tile Placement"},
                    new String[] {"Dice", "Medieval"},
                    null,
                    "",
                    null,
                    new AppCategory(true, false)),
            new BoardGame("000007", "Qwirkle", "", "", "", "https://cf.geekdo-images.com/images/pic309353_t.jpg",
                    2, 4,
                    "4",
                    45,
                    new String[] {"Hand Management", "Pattern Building", "Tile Placement"},
                    new String[] {"Abstract Strategy"},
                    null,
                    "",
                    null,
                    new AppCategory(true, false)),
            new BoardGame("000008", "Saint Petersburg", "", "", "", "https://cf.geekdo-images.com/images/pic246360_t.jpg",
                    2, 4,
                    "4",
                    45,
                    new String[] {"Card Drafting", "Set collection"},
                    new String[] {"Economic", "Age of reason", "Card Game"},
                    null,
                    "",
                    null,
                    new AppCategory(true, false)),
            new BoardGame("0000009", "Friday", "", "", "", "https://cf.geekdo-images.com/images/pic1513328_t.jpg",
                    1, 2,
                    "1",
                    20,
                    new String[] {"Deck/Pool Building", "Hand Management"},
                    new String[] {"Fighting", "Pirates", "Card Game"},
                    null,
                    "",
                    null,
                    new AppCategory(true, false)),
            new BoardGame("0000010", "Hungry Hungry Hippo", "", "", "", "https://cf.geekdo-images.com/images/pic152043_t.jpg",
                    2, 4,
                    "4",
                    10,
                    new String[] {"Deck/Pool Building", "Hand Management"},
                    new String[] {"Fighting", "Pirates", "Card Game"},
                    null,
                    "",
                    null,
                    new AppCategory(true, false)),
            new BoardGame("0000011", "Zuc Zac", "", "", "", "https://cf.geekdo-images.com/images/pic3081186_t.png",
                    2, 6,
                    "3-4",
                    30,
                    new String[] {"Dice Rolling", "Player Elimination", "Press Your Luck"},
                    new String[] {"Education", "Dice", "Card Game"},
                    null,
                    "",
                    null,
                    new AppCategory(true, false)),


    };

//    public List<Paragraph> list = new ArrayList<>();

    public static List<BoardGame> boardGamesList = Arrays.asList(boardGameArray);

    public static List<BoardGame> strategyList;

    public static List<BoardGame> familyList;

    public static List<BoardGame> comingSoonList;

    public static List<BoardGame> hotList;

    public static void setUpList() {
        comingSoonList = new ArrayList<>();
        BoardGame.familyList = new ArrayList<>();
        BoardGame.strategyList = new ArrayList<>();
        hotList = new ArrayList<>();

        strategyList.add(boardGameArray[5]);
        strategyList.add(boardGameArray[6]);
        strategyList.add(boardGameArray[7]);
        strategyList.add(boardGameArray[8]);
        strategyList.add(boardGameArray[4]);

        familyList.add(boardGameArray[9]);
        familyList.add(boardGameArray[10]);

        comingSoonList.add(boardGameArray[2]);
        comingSoonList.add(boardGameArray[3]);
        comingSoonList.add(boardGameArray[1]);

        hotList.add(boardGameArray[0]);

    }
}
