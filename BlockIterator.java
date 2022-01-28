import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write an iterator class that converts a string iterator into a block
 * iterator (list of string iterator), where the start of blocks are marked by
 * strings matching a user-supplied regexp.
 *
 * The {@code inputIterator} returns one string at a time. {@code startOfBlock}
 * is a regex that if matches against an
 * element in the input, marks the start of a block.
 * A block starts from a string that matches {@code startOfBlock} and ends
 * either right before the starts of next block, or at the end of the
 * {@code inputIterator}.
 *
 * Example:
 * inputIterator: ["123", "start", "data1", "data2", "start", "data3", "start"]
 * startOfBlock: "start"
 *
 * Then upon calling next() we will get the following:
 * next() ==> ["start", "data1", "data2"]
 * next() ==> ["start", "data3"]
 * next() ==> ["start"]
 * next() ==> throw {@link java.util.NoSuchElementException}
 */
public class BlockIterator implements Iterator<List<String>> {
    Iterator<String> iterator;
    Pattern pattern;
    String prefix = "";

    public BlockIterator(Iterator<String> inputIterator, Pattern startOfBlock){
        this.iterator = inputIterator;
        this.pattern = startOfBlock;
        while (iterator.hasNext()){
            String temp = iterator.next();
            Matcher m = startOfBlock.matcher(temp);
            if(m.matches()){
                prefix = temp;
                break;
            }
        }
    }

    /**
     * If this iterator has more elements in it, return true without advancing
     * the internal iterator pointer.
     * Otherwise return false.
     */
    @Override
    public boolean hasNext() {
         if(prefix == "") return false;
         else {
             return true;
         }
    }

    /**
     * Return the next block. If no more block exists, throw
     * {@link java.util.NoSuchElementException}.
     */
    @Override
    public List<String> next() {
        List<String> stringList = new ArrayList<>();
        stringList.add(prefix);
        while(iterator.hasNext()){
            String temp = iterator.next();
            Matcher m = pattern.matcher(temp);
            if(m.matches()){
                break;
            }
            stringList.add(temp);
        }
        if(!iterator.hasNext()){
            prefix = "";
        }
        return stringList;
    }
}
