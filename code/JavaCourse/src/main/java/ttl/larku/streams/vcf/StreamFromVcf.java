package ttl.larku.streams.vcf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class StreamFromVcf {

    public static void main(String[] args) throws IOException {
        StreamFromVcf sfv = new StreamFromVcf();
        sfv.go();
    }

    public void go() throws IOException {
        Stream<String> ss = Files.lines(Paths.get("src/main/resources/fakecontacts.vcf"));
        VCFSpliterator vcf = new VCFSpliterator(ss);
        Stream<Contact> contactStream = StreamSupport.stream(vcf, false);

        List<Contact> contacts = contactStream.collect(toList());
        contacts.forEach(System.out::println);
        System.out.println("size: " + contacts.size());

    }
}

/**
 * A Spliterator that will allow us to turn a multiline vcf file into a
 * Stream of Contacts.
 */

class VCFSpliterator implements Spliterator<Contact> {

    private final static String beginToken = "BEGIN";
    private final static String endToken = "END";

    private final Stream<String> sourceStream;
    private final Spliterator<String> sourceSpliterator;
    private boolean seenBegin = false;

    /**
     * We are going to wrap ourselves around the original Spliterator.
     * @param sourceStream
     */
    public VCFSpliterator(Stream<String> sourceStream) {
        this.sourceStream = sourceStream;
        this.sourceSpliterator = sourceStream.spliterator();
    }

    private Contact contact;

    /**
     * This tryAdvance will be called by our downstream operation.
     * We, in turn, call the upstream tryAdvance and keep returning it's
     * result till we get to a "BEGIN" token.  (all of those previous
     * lines would be blank lines).
     * Once we get a BEGIN token, we create a new Contact object and then
     * keep calling tryAdvance on our wrapped Spliterator to keep getting
     * lines from upstream.  We keep updating our Contact properties till
     * we come across the "END" token.  At this point we have captured
     * all given properties, so *now* we call accept on the downstream
     * consumer and give it our new Contact object.  Then it's back to the
     * beginning again.
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Contact> action) {
        boolean sourceResult = sourceSpliterator.tryAdvance(line -> {
            if(line.contains(":")) {  //takes care of blank lines too
                String token = line.substring(0, line.indexOf(":"));
                if (token.equals(beginToken)) {
                    seenBegin = true;
                    contact = new Contact();
                }
            }
        });

        while (sourceResult && seenBegin) {
            //Keep asking the upstream for lines till you come across
            //and END token
            sourceResult = sourceSpliterator.tryAdvance(line -> {
                if (line.startsWith("END")) {
                    seenBegin = false;
                    //Send the new Contact downStream
                    action.accept(contact);
                } else {
                    setProperty(line, contact);
                }
            });
        }

        return sourceResult;
    }

    @Override
    public Spliterator<Contact> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return sourceSpliterator.characteristics();
    }

    private void setProperty(String line, Contact contact) {
        String token = line.split("\\W")[0];
        switch (token) {
            case "VERSION":
                contact.setVcfVersion(line);
                break;
            case "N":
                contact.setName(line);
                break;
            case "FN":
                contact.setFullName(line);
                break;
            case "ORG":
                contact.setOrganization(line);
                break;
            case "TITLE":
                contact.setTitle(line);
                break;
            case "PHOTO":
                contact.setPhoto(line);
                break;
            case "TEL":
                contact.setPhoneNumber(line);
                break;
            case "REV":
                contact.setCreateTime(line);
                break;
        }
    }
}
