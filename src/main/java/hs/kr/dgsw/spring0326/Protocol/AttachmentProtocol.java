package hs.kr.dgsw.spring0326.Protocol;

public class AttachmentProtocol {
    private String storedPath;
    private String originalName;

    public AttachmentProtocol() {
    }

    public AttachmentProtocol(String storedPath, String originalName) {
        this.storedPath = storedPath;
        this.originalName = originalName;
    }

    public String getStoredPath() {
        return storedPath;
    }

    public void setStoredPath(String storedPath) {
        this.storedPath = storedPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
