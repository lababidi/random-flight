import Twitter.Message;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * Created by mahmoud on 2/25/15.
 */
public class Converter {

    ObjectMapper mapper;

    public Converter(){
        mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        String dateFormat = "EEE MMM dd HH:mm:ss ZZZZZ YYYY"; //'Sat Nov 08 10:42:09 +0000 2014'
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));

    }
    public Message convert(String jsonString) throws IOException {

        //Sometimes the json message from Twitter is a Rate-Limiting warning
        if(jsonString.length()<10 || jsonString.startsWith("{\"limit\":"))
            throw new IOException();

        return mapper.readValue(jsonString, Message.class);


    }
}
