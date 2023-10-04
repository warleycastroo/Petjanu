public class TipItem {
    private String title;
    private int imageResource;

    public TipItem() {
        // Construtor vazio necessário para o Firebase
    }

    public TipItem(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}
