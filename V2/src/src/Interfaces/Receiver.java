package Interfaces;

public interface Receiver {
    /**
     * Copy a selected fragment of content
     * @return selected string
     */
    void copy(int start, int stop);

    /**
     * Insert character at specific position
     * @param c char to inset
     * @param position insert position
     */
    void insert(String c, int position);

    /**
     * Insert clipboard at specific position
     * @param position insert position
     */
    void insertClipboard(int position);

    /**
     * Delete selected content
     * @param start : start point
     * @param stop : end point
     */
    void delete(int start, int stop);
}
