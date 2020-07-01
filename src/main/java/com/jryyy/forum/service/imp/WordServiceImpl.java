package com.jryyy.forum.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.constant.WordWeight;
import com.jryyy.forum.dao.ThesaurusMapper;
import com.jryyy.forum.dao.WordProgressMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.Word;
import com.jryyy.forum.model.WordProgress;
import com.jryyy.forum.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final WordProgressMapper wordProgressMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public WordServiceImpl(ThesaurusMapper thesaurusMapper, WordProgressMapper wordProgressMapper, RedisTemplate<String, Object> redisTemplate) {
        this.thesaurusMapper = thesaurusMapper;
        this.wordProgressMapper = wordProgressMapper;
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
        wordProgressMapper.insertProgress(userId);
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
            //是否记忆成功
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
        wordProgressMapper.deleteProgress(userId);
        return new Response();
    }

    @Override
    public Response progress(Integer userId) throws Exception {
        LocalDate today = LocalDate.now();
        WordProgress wordProgress = wordProgressMapper.findWordProgressByUserId(userId);
        if (today.isBefore(wordProgress.getModifyDate())) {
            wordProgressMapper.updateCurrent(userId, 0);
        }
        WordProgress progress = wordProgressMapper.findWordProgressByUserId(userId);
        progress.setCurrentPosition(redisTemplate.opsForList().size(KayOrUrl.memorizedWordsKey(userId)));
        return new Response<>(progress);
    }

    @Override
    public Response dailyDuty(Integer userId, Integer dailyDuty) throws Exception {
        if (wordProgressMapper.updateDailyDuty(userId, dailyDuty) == 0) {
            throw new GlobalException(GlobalStatus.thesaurusIsNotApplied);
        }
        return new Response<>("更改每日任务量成功");
    }

    /**
     * 单词记忆权重规则
     */
    private void wordSort(Integer userId, Word word) throws Exception {
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
            WordProgress wordProgress = wordProgressMapper.findWordProgressByUserId(userId);
            wordProgressMapper.updateCurrent(userId, wordProgress.getCurrentDailyPosition() + 1);
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

