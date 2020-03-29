package com.jryyy.forum.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.constant.WordWeight;
import com.jryyy.forum.dao.ThesaurusMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.Word;
import com.jryyy.forum.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * 单词记忆服务
 *
 * @author OU
 */
@Slf4j
@Service
public class WordServiceImpl implements WordService {

    private static final long ONLINE_THESAURUS_LENGTH = 25;
    private final ThesaurusMapper thesaurusMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public WordServiceImpl(ThesaurusMapper thesaurusMapper, RedisTemplate<String, Object> redisTemplate) {
        this.thesaurusMapper = thesaurusMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response getWordLibrary(Integer userId) throws Exception {
        Random random = new Random();
        List<Word> words = thesaurusMapper.findAllWords();
        redisTemplate.delete(KayOrUrl.unrecordedWordsKey(userId));
        redisTemplate.delete(KayOrUrl.memorizingWordsKey(userId));
        words.forEach(word -> redisTemplate.opsForList().rightPush(KayOrUrl.unrecordedWordsKey(userId),
                JSONObject.toJSONString(word)));
        for (int i = 0; i < ONLINE_THESAURUS_LENGTH; i++) {
            String word = (String) redisTemplate.opsForList().index(
                    KayOrUrl.unrecordedWordsKey(userId), random.nextInt(Constants.TOTAL_WORDS));
            log.info(word);
            redisTemplate.opsForList().remove(KayOrUrl.unrecordedWordsKey(userId), 0, word);
            redisTemplate.opsForList().leftPush(KayOrUrl.memorizingWordsKey(userId), word);
        }
        redisTemplate.delete(KayOrUrl.memorizedWordsKey(userId));
        return new Response<>("词库创建完成");
    }

    @Override
    public Response memory(Integer userId, Boolean understanding) throws Exception {
        if (redisTemplate.opsForList().size(KayOrUrl.unrecordedWordsKey(userId)) == 0L) {
            throw new GlobalException(GlobalStatus.thesaurusIsNotApplied);
        }
        Word word = JSONObject.parseObject((String) redisTemplate.opsForList().
                index(KayOrUrl.memorizingWordsKey(userId), 0), Word.class);
        if (understanding != null) {
            if (understanding) {
                assert word != null;
                if (word.getWeight() == WordWeight.default_level_weighting) {
                    word.setWeight(WordWeight.FIVE_LEVEL_WEIGHTING);
                } else {
                    word.setWeight(word.getWeight() + 1);
                }
            } else {
                assert word != null;
                if (word.getWeight() == WordWeight.default_level_weighting) {
                    word.setWeight(WordWeight.FIVE_LEVEL_WEIGHTING);
                } else if (word.getWeight() != WordWeight.one_level_weighting) {
                    word.setWeight(word.getWeight() - 1);
                }
            }
            wordSort(userId, word);
        }
        return new Response<>(JSONObject.parseObject((String) redisTemplate.opsForList().
                index(KayOrUrl.memorizingWordsKey(userId), 0), Word.class));
    }

    @Override
    public Response review(Integer userId) throws Exception {
        return null;
    }

    @Override
    public Response remove(Integer userId) throws Exception {
        redisTemplate.delete(KayOrUrl.unrecordedWordsKey(userId));
        redisTemplate.delete(KayOrUrl.memorizingWordsKey(userId));
        redisTemplate.delete(KayOrUrl.memorizedWordsKey(userId));
        return new Response();
    }

    private void wordSort(Integer userId, Word word) {
        Random random = new Random();
        String wordJSONString = JSONObject.toJSONString(word);
        List<Object> words = redisTemplate.opsForList().range(KayOrUrl.memorizingWordsKey(userId), 0, -1);
        assert words != null;
        words.remove(0);
        if (word.getWeight() == WordWeight.SIX_LEVEL_WEIGHTING) {
            String w = (String) redisTemplate.opsForList().index(KayOrUrl.unrecordedWordsKey(userId),
                    random.nextInt(Constants.TOTAL_WORDS));
            log.info(w);
            redisTemplate.opsForList().rightPush(KayOrUrl.memorizedWordsKey(userId), wordJSONString);
            words.add(w);
        } else if (word.getWeight() == WordWeight.FIVE_LEVEL_WEIGHTING) {
            words.add(2 + random.nextInt(4), wordJSONString);
        } else if (word.getWeight() == WordWeight.four__level_weighting ||
                word.getWeight() == WordWeight.three__level_weighting) {
            words.add(5 + random.nextInt(6), wordJSONString);
        } else if (word.getWeight() == WordWeight.two_level_weighting ||
                word.getWeight() == WordWeight.one_level_weighting) {
            words.add(11 + random.nextInt(4), wordJSONString);
        }

        redisTemplate.delete(KayOrUrl.memorizingWordsKey(userId));
        words.forEach(o -> redisTemplate.opsForList().rightPush(KayOrUrl.memorizingWordsKey(userId), o));
    }
}

