package junkeaz.junkeaz;



public class JunkeazListing {

    private int id;
    private String imageUrl;
    private String title;
    private String postingUser;
    private String description;
    private String streetAddress;
    private String claimStatus;
    private String claimingUser;
    private String postingUserEmail;
    private String claimingUserEmail;
    private String postingUserId;
    private String claimingUserId;

    public JunkeazListing(int id, String imageUrl, String title, String postingUser, String description, String streetAddress, String claimStatus) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.postingUser = postingUser;
        this.description = description;
        this.streetAddress = streetAddress;
        this.claimStatus = claimStatus;
    }

    //Getters and Setters
    public int getId() {return id;}

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostingUser() {
        return postingUser;
    }

    public void setPostingUser(String postingUser) {
        this.postingUser = postingUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public void setClaimingUserId(String claimingUserId) { this.claimingUserId = claimingUserId; }

    public String getClaimingUserId() { return claimingUserId; }

    public void setClaimingUserEmail(String claimingUserEmail) { this.claimingUserEmail = claimingUserEmail; }

    public String getClaimingUserEmail() { return claimingUserEmail; }

    public void setClaimingUser(String claimingUser) { this.claimingUser = claimingUser; }

    public String getClaimingUser() { return claimingUser; }

    public void setPostingUserEmail(String postingUserEmail) { this.postingUserEmail = postingUserEmail; }

    public String getPostingUserEmail() { return postingUserEmail; }

    public void setPostingUserId(String postingUserId) { this.postingUserId = postingUserId; }

    public String getPostingUserId() { return postingUserId; }
}
