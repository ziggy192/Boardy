package techkids.cuong.myapplication.models;

/**
 * Created by Nghia on 2/18/2017.
 */

public class CardDetail {
    private String cardName;
    private String imageUrl;
    private String content;

    public CardDetail(String cardName,String imageUrl, String content) {
        this.cardName = cardName;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    public String getCardName() {
        return cardName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContent() {

        return content;
    }

    public static final CardDetail[] cardDetails =
            {new CardDetail("Tiên tri", "https://raw.githubusercontent.com/cuonghq104/Images/master/Card/seer.png",
                    "Mỗi đêm, khi được gọi dậy Tiên Tri sẽ chỉ một người. Nếu người đó là Sói Quản trò sẽ gật đầu."),

                    new CardDetail("Thợ săn", "https://raw.githubusercontent.com/cuonghq104/Images/master/Card/hunter.png", "Khi thợ săn chết, dù là dưới bất kỳ hình thức nào cũng có thể chọn một người chơi khác và kéo hắn xuống “Tuyền đường” cùng Thợ Săn"),
                    new CardDetail("Phù thủy", "https://raw.githubusercontent.com/cuonghq104/Images/master/Card/witch.png", "Phù Thủy có hai bình thuốc: Một bình dùng để cứu một người, còn một bình dùng để giết một người. Mỗi đêm, Quản trò khi gọi Phù Thủy dậy sẽ cho Phù Thủy biết người bị giết bởi Sói đêm đó, và Phù Thủy được quyền quyết định xem có cứu người ấy hay không. Sau đó, quản trò sẽ hỏi xem Phù Thủy có dùng bình giết giết ai hay không. Một khi đã dùng bình thì Phù Thủy sẽ mất đi chức năng tương ứng, tuy nhiên vẫn được gọi dậy mỗi đêm và biết ai chết. Lưu ý: Nếu đêm đó Bảo Vệ đã Bảo Vệ đúng người thì Quản trò sẽ lắc tay, ra dấu là không ai chết cả."),
                    new CardDetail("Cupid", "https://raw.githubusercontent.com/cuonghq104/Images/master/Card/cupid.png", "Đầu mỗi ván chơi, Cupid sẽ được gọi dậy và chọn ra hai người yêu nhau. Cupid sau đó nhắm mắt lại và hai người yêu nhau sẽ được Quản Trò gọi dậy để biết mặt và Vai Trò của nhau. Nếu hai người thuộc hai phe khác nhau (Sói vs Dân) thì họ thành phe thứ ba với nhiệm vụ là hai người cuối cùng sống sót."),
                    new CardDetail("Ma Sói", "https://raw.githubusercontent.com/cuonghq104/Images/master/Card/WereWolf.jpg", "Mỗi đêm thức dậy, các Sói sẽ biết mặt lẫn nhau và sẽ cùng thống nhất giết một người. Sói có quyền không giết, và cũng có quyền tự giết lẫn nhau."),
                    new CardDetail("Dân làng", "https://raw.githubusercontent.com/cuonghq104/Images/master/Card/villager.png", "Năng lực rất đơn giản: Chết khi bị giết. Ngoài ra còn có năng lực bỏ phiếu treo người khác")};

}
