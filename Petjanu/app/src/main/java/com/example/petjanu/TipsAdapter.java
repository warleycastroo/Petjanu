public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipViewHolder> {

    private List<TipItem> tipItemList;
    private DatabaseReference mDatabase;

    public TipsAdapter(List<TipItem> tipItemList) {
        this.tipItemList = tipItemList;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("dicas"); // "dicas" é o nome do nó onde você armazena as dicas no Firebase
    }

    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        TipItem tipItem = tipItemList.get(position);
        holder.tipImageView.setImageResource(tipItem.getImageResource());
        holder.tipTitleTextView.setText(tipItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return tipItemList.size();
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {

        ImageView tipImageView;
        TextView tipTitleTextView;

        TipViewHolder(@NonNull View itemView) {
            super(itemView);
            tipImageView = itemView.findViewById(R.id.tipImageView);
            tipTitleTextView = itemView.findViewById(R.id.tipTitleTextView);
        }
    }

    public void loadTipsFromFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tipItemList.clear();
                for (DataSnapshot tipSnapshot : dataSnapshot.getChildren()) {
                    TipItem tipItem = tipSnapshot.getValue(TipItem.class);
                    tipItemList.add(tipItem);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trate os erros aqui
            }
        });
    }
}
