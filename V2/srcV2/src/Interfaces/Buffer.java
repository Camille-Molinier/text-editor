package Interfaces;

public interface Buffer {
    /**
     * Add content at indicate position
     * @param s String to insert
     * @param position indicate position
     */
    void addContent(String s, int position);

    /**
     * Delete content between start and stop positions
     * @param start beginning point
     * @param stop ending point
     */
    void deleteContent(int start, int stop);

    /**
     * Get all buffer's content
     * @return buffer's string
     */
    String getContent();
}
