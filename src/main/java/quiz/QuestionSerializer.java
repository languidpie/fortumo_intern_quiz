package quiz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class QuestionSerializer implements JsonSerializer<Question> {

    @Override
    public JsonElement serialize(Question src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("question", src.getQuestion());
        result.addProperty("category", src.getCategory());
        result.addProperty("difficulty", src.getDifficulty());

        return context.serialize(result, typeOfSrc);
    }
}
