package cn.hzjdemo.hellodiary.util;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.zenchn.support.utils.StringUtils;
import com.zenchn.support.widget.enteringlayout.EnteringLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作    者：wangr on 2017/8/28 11:25
 * 描    述：
 * 修订记录：
 */

public class EditTextUtils {

    /**
     * 过滤超过四个字节的字符正则
     */
    private static final String OVER_FOUR_BYTE_REGEX = "[\\uD800\\uDC00-\\uDBFF\\uDFFF]";
    /**
     * 目前所有emoji过滤正则（目前挺准）
     */
    private static final String FROM_NOW_ALL_EMOJI_REGEX = "(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83E\uDD00-\uD83E\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)";

    /**
     * 只允许汉字正则
     */
    private static final String CHINESE_REGEX = "[^\u4E00-\u9FA5]";
    private static final String REGEX_SPACE = " ";

    //非中文回调
    private static OnNonChineseInputCallback mOnNonChineseInputCallback;

    public interface OnNonChineseInputCallback {
        void onNonChineseInput();
    }

    /**
     * 非中文过滤
     */
    private static InputFilter sChineseFilter = new InputFilter() {
        Pattern pattern = Pattern.compile(CHINESE_REGEX, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = pattern.matcher(source);
            if (matcher.find()) {
                if (mOnNonChineseInputCallback != null) {
                    mOnNonChineseInputCallback.onNonChineseInput();
                }
                return "";
            }

            return null;
        }
    };

    /**
     * emoji过滤
     */
    private static InputFilter sEmojiFilter = new InputFilter() {
        Pattern pattern = Pattern.compile(FROM_NOW_ALL_EMOJI_REGEX, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = pattern.matcher(source);
            if (matcher.find()) {
                return "";
            }
            return null;
        }
    };
    /**
     * 空格，回车过滤
     */
    private static InputFilter sSpaceFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String resultString = source.toString();
            //过滤空格
            if (resultString.contains(REGEX_SPACE)) {
                StringBuilder sb = new StringBuilder();
                String[] str = resultString.split(REGEX_SPACE);
                for (String s : str) {
                    sb.append(s);
                }
                resultString = sb.toString();
            }
            return resultString;
        }
    };


    /**
     * 设置小数位过滤器
     *
     * @param et
     * @param maxIntegerLength 整数位最大长度
     * @param maxDecimalLength 小数位最大长度
     */

    public static void setDecimalFilter(EditText et, final int maxIntegerLength, final int maxDecimalLength) {
        if (et != null) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String temp = s.toString();
                    //不以0开头
                    int len = s.toString().length();
                    if (len > 1 && temp.startsWith("0")) {
                        s.replace(0, 1, "");
                    }
                    //小数点后保留n位小数
                    if (StringUtils.isNonNull(temp)) {
                        int posDot = temp.indexOf(".");
                        int length = temp.length();
                        if (posDot <= 0) {
                            if (length >= maxIntegerLength + 1) {
                                s.delete(maxIntegerLength, maxIntegerLength + 1);
                            }
                        } else {
                            if ((temp.length() - 1) - posDot > maxDecimalLength) {
                                s.delete(length - 1, length);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 设置小数位过滤器
     *
     * @param et
     * @param maxIntegerLength 整数位最大长度
     * @param maxDecimalLength 小数位最大长度
     * @param changeColor      监听文字发生变化，即刻改变字体颜色
     */

    public static void setDecimalFilter(EditText et, final int maxIntegerLength, final int maxDecimalLength, int originColor, int changeColor) {
        if (et != null) {
            String originString = et.getText().toString();
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String temp = s.toString();
                    //不以0开头
                    int len = s.toString().length();
                    if (len > 1 && temp.startsWith("0")) {
                        s.replace(0, 1, "");
                    }
                    //小数点后保留n位小数
                    if (StringUtils.isNonNull(temp)) {
                        int posDot = temp.indexOf(".");
                        int length = temp.length();
                        if (posDot <= 0) {
                            if (length >= maxIntegerLength + 1) {
                                s.delete(maxIntegerLength, maxIntegerLength + 1);
                            }
                        } else {
                            if ((temp.length() - 1) - posDot > maxDecimalLength) {
                                s.delete(length - 1, length);
                            }
                        }
                    }

                    if (!originString.equals(s.toString())) {
                        et.setTextColor(changeColor);
                    } else {
                        et.setTextColor(originColor);
                    }
                }
            });
        }
    }

    /**
     * 设置数字不能以0开头
     *
     * @param et
     */
    public static void setNoStartWithZeroFilter(EditText et) {
        if (et != null) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    int len = s.toString().length();
                    if (len > 1 && text.startsWith("0")) {
                        s.replace(0, 1, "");
                    }
                }
            });
        }
    }

    /**
     * 设置数字不能以0开头,并监听文字变化改变字体颜色
     *
     * @param et
     * @param changeColor 新的字体颜色
     */
    public static void setNoStartWithZeroFilter(EditText et, int originColor, int changeColor) {
        if (et != null) {
            String originString = et.getText().toString();
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    int len = s.toString().length();
                    if (len > 1 && text.startsWith("0")) {
                        s.replace(0, 1, "");
                    }

                    if (!s.toString().equals(originString)) {
                        et.setTextColor(changeColor);
                    } else {
                        et.setTextColor(originColor);
                    }
                }
            });
        }
    }

    /**
     * 设置数字不能以0开头
     *
     * @param enteringLayout
     */
    public static void setNoStartWithZeroFilter(@NonNull EnteringLayout enteringLayout) {
        View customView = enteringLayout.getCustomView();
        if (customView instanceof EditText) {
            EditText et = (EditText) customView;
            setNoStartWithZeroFilter(et);
        }
    }

    /**
     * 设置数字不能以0开头
     *
     * @param enteringLayout
     * @param changeColor    新的字体颜色，监听文字发生变化即刻改变字体颜色
     */
    public static void setNoStartWithZeroFilter(@NonNull EnteringLayout enteringLayout, int originColor, int changeColor) {
        View customView = enteringLayout.getCustomView();
        if (customView instanceof EditText) {
            EditText et = (EditText) customView;
            setNoStartWithZeroFilter(et, originColor, changeColor);
        }
    }


    public static void setMaxLengthFilter(@NonNull EditText et, int maxLength) {
        if (et != null) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength > 0 ? maxLength : 100)});
        }
    }

    /**
     * 设置emoji过滤器
     *
     * @param et
     */
    public static void setEmojiFilter(EditText et) {
        if (et != null) {
            et.setFilters(new InputFilter[]{sEmojiFilter});
        }
    }

    /**
     * 设置emoji,空格过滤器
     *
     * @param et
     */
    public static void setEmojiAndSpaceFilter(EditText et) {
        if (et != null) {
            et.setFilters(new InputFilter[]{sEmojiFilter, sSpaceFilter});
        }
    }

    /**
     * 设置emoji，长度过滤
     *
     * @param et
     * @param maxLength
     */
    public static void setEmojiAndLengthFilters(@NonNull EditText et, int maxLength) {
        if (et != null) {
            InputFilter[] filterArray = {new InputFilter.LengthFilter(maxLength > 0 ? maxLength : 100),
                    sEmojiFilter};
            et.setFilters(filterArray);
        }
    }

    /**
     * 只能输入汉字
     *
     * @param et
     */
    public static void setChineseEmojiAndLengthFilters(@NonNull EditText et, int maxLength, OnNonChineseInputCallback callback) {
        if (et != null) {
            mOnNonChineseInputCallback = callback;
            et.setFilters(new InputFilter[]{sChineseFilter, sEmojiFilter, new InputFilter.LengthFilter(maxLength)});
        }
    }

    /**
     * 移除汉字监听，避免内存泄漏
     */
    public static void removeChineseInputCallback() {
        mOnNonChineseInputCallback = null;
    }
}
