package com.amindset.ve.amindset.TextChat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.amindset.ve.amindset.BaseActivity.BaseActivity;
import com.amindset.ve.amindset.Constant;
import com.amindset.ve.amindset.R;
import com.amindset.ve.amindset.TextChat.ModelTextChat.ModelTextChatNotification;
import com.amindset.ve.amindset.Web.ApiClient;
import com.amindset.ve.amindset.Web.WebService;
import com.amindset.ve.amindset.data.AppPreferencesHelper;
import com.squareup.picasso.Picasso;
import com.twilio.chat.*;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends BaseActivity implements View.OnClickListener {


   public static boolean active = false;    /*
       Change this URL to match the token URL for your Twilio Function
    */
//    final static String SERVER_TOKEN_URL = "https://shadow-labradoodle-3440.twil.io/chat-token";
//    https://shadow-labradoodle-3440.twil.io/videoToken
    final static String DEFAULT_CHANNEL_NAME = "general";
    final static String TAG = "TwilioChat";

    AppPreferencesHelper preferencesHelper;

    // Update this identity for each individual user, for instance after they login
    private String mIdentity = "CHAT_USER";

    private RecyclerView mMessagesRecyclerView;
    private MessagesAdapter mMessagesAdapter;
    private ArrayList<Message> mMessages = new ArrayList<>();

    private EditText mWriteMessageEditText;
    private Button mSendChatMessageButton;

    private ChatClient mChatClient;

    private static Channel mGeneralChannel;
    private boolean abc;
    private TextView tv_username;
    private CircleImageView profile_image;
    private ImageView iv_toobar_back;
    private TextView tv_endChat;


    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textchat);
        showLoading();
        preferencesHelper = new AppPreferencesHelper(this);
        tv_username=(TextView)findViewById(R.id.tv_username);
        tv_endChat=(TextView)findViewById(R.id.tv_endChat);
        tv_endChat.setOnClickListener(this);
        profile_image=(CircleImageView) findViewById(R.id.profile_image);
        iv_toobar_back=(ImageView) findViewById(R.id.iv_toobar_back);
        iv_toobar_back.setOnClickListener(this);
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.messagesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // for a chat app, show latest at the bottom
        layoutManager.setStackFromEnd(true);
        mMessagesRecyclerView.setLayoutManager(layoutManager);

        mMessagesAdapter = new MessagesAdapter();
        mMessagesRecyclerView.setAdapter(mMessagesAdapter);
        mWriteMessageEditText = (EditText) findViewById(R.id.writeMessageEditText);
        mSendChatMessageButton = (Button) findViewById(R.id.sendChatMessageButton);


        if (getIntent().getStringExtra("providerName")!=null) {
            tv_username.setText(getIntent().getStringExtra("providerName"));

        }

        if (getIntent().getStringExtra("providerImage")!=null) {
            Picasso.get().load(getIntent().getStringExtra("providerImage")).into(profile_image);
        }


//                "userName":"kamlesh"
        if (getIntent().getStringExtra("providerNumber") != null) {

            retrieveAccessTokenfromServer(getIntent().getStringExtra("providerNumber")
                    ,getIntent().getStringExtra("providerName"),
                    getIntent().getStringExtra("providerImage"));
            abc = false;

        } else {
            retrieveAccessTokenfromServer(preferencesHelper.getUserP_phone(),
                    preferencesHelper.getUserUser_name(),preferencesHelper.getUserP_Prof_pic());
            abc = true;

        }

        mSendChatMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGeneralChannel != null) {
                    String messageBody = mWriteMessageEditText.getText().toString();
                    if(!TextUtils.isEmpty(messageBody))
                    {
                        Message.Options options = Message.options().withBody(messageBody);
                        Log.d(TAG, "Message created");

                        mGeneralChannel.getMessages().sendMessage(options, new CallbackListener<Message>() {
                            @Override
                            public void onSuccess(Message message) {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // need to modify user interface elements on the UI thread
                                        mWriteMessageEditText.setText("");
                                        mMessagesRecyclerView.scrollToPosition(mMessagesAdapter.getItemCount());
                                    }
                                });
                            }
                        });
                    }
                }}
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void retrieveAccessTokenfromServer(String mobileNumber , String name, String image) {
        WebService apiService =
                ApiClient.getClient().create(WebService.class);
        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("mobile_no", mobileNumber);

        Call<ModelTextChatToken> call = apiService.getTextChatAccessToken(paramObject);

        call.enqueue(new retrofit2.Callback<ModelTextChatToken>() {
            @Override
            public void onResponse(Call<ModelTextChatToken> call, Response<ModelTextChatToken> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        setTitle(mIdentity);
                        if (abc) {
                            ApiForNotification(getIntent().getStringExtra("roomNameprovider"),name,image);
                        }
                        ChatClient.Properties.Builder builder = new ChatClient.Properties.Builder();
                        ChatClient.Properties props = builder.createProperties();
                        ChatClient.create(MainActivity.this, response.body().getData().getToken(), props, mChatClientCallback);
                    } else {
                        showMessage("Error onResponse");
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTextChatToken> call, Throwable t) {
                // Log error here since request failed
                showMessage("Error onFailure");

            }
        });
    }

    private void ApiForNotification(String mobileNumber ,String name,String image) {
        WebService apiService =
                ApiClient.getClient().create(WebService.class);
        HashMap<String, String> paramObject = new HashMap<>();

        paramObject.put("to_number", mobileNumber);
        paramObject.put("my_profile_pic_url", image);
        paramObject.put("type", "chat");
        paramObject.put("userName", name);

        Call<ModelTextChatNotification> call = apiService.getTextNotification(paramObject, "Bearer " + preferencesHelper.getUserBToken());

        call.enqueue(new retrofit2.Callback<ModelTextChatNotification>() {
            @Override
            public void onResponse(Call<ModelTextChatNotification> call, Response<ModelTextChatNotification> response) {


                if (response != null) {
                    if (response.isSuccessful() && response.body().getStatus()== Constant.RESPONSE_SUCCESSFULLY) {

                    } else {
                        showMessage(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTextChatNotification> call, Throwable t) {
                // Log error here since request failed
                showMessage("Error onFailure");

            }
        });
    }

    private void loadChannels() {

        mChatClient.getChannels().getChannel(DEFAULT_CHANNEL_NAME, new CallbackListener<Channel>() {
            @Override
            public void onSuccess(Channel channel) {
                if (channel != null) {
                    Log.d(TAG, "Joining Channel: " + DEFAULT_CHANNEL_NAME);
                    joinChannel(channel);
                } else {
                    Log.d(TAG, "Creating Channel: " + DEFAULT_CHANNEL_NAME);

                    mChatClient.getChannels().createChannel(DEFAULT_CHANNEL_NAME,
                            Channel.ChannelType.PUBLIC, new CallbackListener<Channel>() {
                                @Override
                                public void onSuccess(Channel channel) {
                                    if (channel != null) {
                                        Log.d(TAG, "Joining Channel: " + DEFAULT_CHANNEL_NAME);


                                        joinChannel(channel);


                                    }
                                }

                                @Override
                                public void onError(ErrorInfo errorInfo) {
                                }
                            });
                }
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
            }

        });

    }

    private void joinChannel(final Channel channel) {
        Log.d(TAG, "Joining Channel: " + channel.getUniqueName());
        hideLoading();


        if (channel.getStatus() == Channel.ChannelStatus.JOINED) {
            // already in the channel, load the messages
            mGeneralChannel = channel;

        } else {
            channel.join(new StatusListener() {
                @Override
                public void onSuccess() {
                    mGeneralChannel = channel;
                    Log.d(TAG, "Joined default channel");
                    mGeneralChannel.addListener(mDefaultChannelListener);
                    hideLoading();

                }

                @Override
                public void onError(ErrorInfo errorInfo) {
                    mGeneralChannel = channel;
                    mGeneralChannel.addListener(mDefaultChannelListener);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mGeneralChannel.getMessages().getLastMessages(50, new CallbackListener<List<Message>>() {
                                @Override
                                public void onSuccess(List<Message> messages) {
                                    for (Message message : messages) {
                                        Log.d(TAG, "Message Body: " + message.getMessageBody());
                                        mMessages.add(message);
                                        mMessagesAdapter.notifyDataSetChanged();

                                    }
                                }
                            });

                        }
                    }, 2000);


                }
            });

        }


    }

    private CallbackListener<ChatClient> mChatClientCallback =

            new CallbackListener<ChatClient>() {
                @Override
                public void onSuccess(ChatClient chatClient) {
                    mChatClient = chatClient;
                    loadChannels();
                    Log.d(TAG, "Success creating Twilio Chat Client");
                }

                @Override
                public void onError(ErrorInfo errorInfo) {
                }
            };

    private ChannelListener mDefaultChannelListener = new ChannelListener() {


        @Override
        public void onMessageAdded(final Message message) {
            Log.d(TAG, "Message added");
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // need to modify user interface elements on the UI thread
                    mMessages.add(message);
                    mMessagesRecyclerView.scrollToPosition(mMessages.size()-1);
                    mMessagesAdapter.notifyDataSetChanged();
                }
            });

        }

        @Override
        public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {
            Log.d(TAG, "Message updated: " + message.getMessageBody());
        }

        @Override
        public void onMessageDeleted(Message message) {
            Log.d(TAG, "Message deleted");
        }

        @Override
        public void onMemberAdded(Member member) {
            Log.d(TAG, "Member added: " + member.getIdentity());
        }

        @Override
        public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {
            Log.d(TAG, "Member updated: " + member.getIdentity());
        }

        @Override
        public void onMemberDeleted(Member member) {
            Log.d(TAG, "Member deleted: " + member.getIdentity());
        }

        @Override
        public void onTypingStarted(Channel channel, Member member) {
            Log.d(TAG, "Started Typing: " + member.getIdentity());
        }

        @Override
        public void onTypingEnded(Channel channel, Member member) {
            Log.d(TAG, "Ended Typing: " + member.getIdentity());
        }

        @Override
        public void onSynchronizationChanged(Channel channel) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_toobar_back:
            {
                Dialog();
            }

            case R.id.tv_endChat:
            {
                Dialog();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Dialog();

//        super.onBackPressed();

    }

    void Dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Pressing button will end your chat session with the provider, press if you would like to procede and end your session.\n")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {


            public LinearLayout mll;
            public TextView mMessageTextViewLeft;
            public TextView mMessageTextViewRight;


            public ViewHolder(LinearLayout mll) {
                super(mll);
                this.mll = mll;

                mMessageTextViewLeft= mll.findViewById(R.id.chat_tv_left);
                mMessageTextViewRight=mll.findViewById(R.id.chat_tv_right);
            }
        }

        public MessagesAdapter() {

        }

        @Override
        public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            LinearLayout rl = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msg, parent, false);
            return new ViewHolder(rl);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Message message = mMessages.get(position);
            String messageText = String.format("%s",message.getMessageBody());

            if(preferencesHelper.getAccountType()!=null && preferencesHelper.getAccountType().equalsIgnoreCase("user")) {
                if (message != null && message.getAuthor() != null && message.getAuthor().equalsIgnoreCase(preferencesHelper.getUserP_phone())) {
                    try {
                        holder.mMessageTextViewRight.setText( messageText + " \n"+ CurrentTime(message.getDateCreatedAsDate()));
                        holder.mMessageTextViewLeft.setText("");
                        holder.mMessageTextViewRight.setVisibility(View.VISIBLE);
                        holder.mMessageTextViewLeft.setVisibility(View.GONE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
//
                else {
                    try {
                        holder.mMessageTextViewLeft.setText(messageText+ " \n"+ CurrentTime(message.getDateCreatedAsDate()));
                        holder.mMessageTextViewRight.setText("");
                        holder.mMessageTextViewLeft.setVisibility(View.VISIBLE);
                        holder.mMessageTextViewRight.setVisibility(View.GONE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }

            else
            {
                if(preferencesHelper.getAccountType()!=null && preferencesHelper.getAccountType().equalsIgnoreCase("provider")) {
                    if (message != null && message.getAuthor() != null && message.getAuthor().equalsIgnoreCase(preferencesHelper.getProviderT_mobile())) {

                        try {
                            holder.mMessageTextViewRight.setText(messageText+ " \n"+ CurrentTime(message.getDateCreatedAsDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        holder.mMessageTextViewLeft.setText("");
                        holder.mMessageTextViewLeft.setBackground(null);
                        holder.mMessageTextViewRight.setVisibility(View.VISIBLE);
                        holder.mMessageTextViewLeft.setVisibility(View.GONE);

                    }
//
                    else {
                        try {
                            holder.mMessageTextViewLeft.setText(messageText+ " \n"+ CurrentTime(message.getDateCreatedAsDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        holder.mMessageTextViewRight.setText("");
                        holder.mMessageTextViewRight.setBackground(null);

                        holder.mMessageTextViewLeft.setVisibility(View.VISIBLE);
                        holder.mMessageTextViewRight.setVisibility(View.GONE);

                    }

                }

            }
        }
        @Override
        public int getItemCount() {
            return mMessages.size();
        }
    }

    public String CurrentTime(Date date) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat
                ("EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
        inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        SimpleDateFormat outputFormat =
                new SimpleDateFormat("h:mm a");
        // Adjust locale and zone appropriately
//      Date datee = inputFormat.parse(String.valueOf(date));
        String outputText = outputFormat.format(date);

        return outputText;
    }
}
