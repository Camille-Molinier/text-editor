package Interfaces;

public interface Buffer {
    /**
     * Add content at indicate position
     * @param s String to insert
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
     * @return buffer's content
     */
    String getContent();

    /**
     * Get content between two positions
     * @param start beginning point
     * @param stop ending point
     * @return buffer's content between start and stop
     */
    String getContent(int start, int stop);
}
