package toloka.service;

import org.springframework.stereotype.Service;
import toloka.Repository.ItemRepositore;
import toloka.Repository.TextBodyRepository;
import toloka.model.blog.TextBody;

@Service("TextBodyService")
public class TextBodyService {
    TextBodyRepository textbodyrepositore;

    public TextBodyService(TextBodyRepository itb) {
        this.textbodyrepositore = itb;
    }

    public TextBody GetBody(Long id) {
        return textbodyrepositore.getOne(id);
    }

    public TextBody SaveBody(TextBody tb) {
        textbodyrepositore.save(tb);
        return tb;
    }
}
