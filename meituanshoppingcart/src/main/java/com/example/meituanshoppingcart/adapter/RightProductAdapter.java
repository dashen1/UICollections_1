package com.example.meituanshoppingcart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituanshoppingcart.R;
import com.example.meituanshoppingcart.entity.ProductListEntity;
import com.example.meituanshoppingcart.entity.ShopCart;
import com.example.meituanshoppingcart.impl.ShopCartImp;

import java.util.ArrayList;

public class RightProductAdapter extends RecyclerView.Adapter {

    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    private final int HEAD_TYPE = 2;

    private Context mContext;
    private ArrayList<ProductListEntity> data;

    private int mItemCount;

    private ShopCart shopCart;
    private ShopCartImp shopCartImp;

    public RightProductAdapter(Context mContext, ArrayList<ProductListEntity> mMenuList, ShopCart shopCart) {
        this.mContext = mContext;
        this.data = mMenuList;
        this.shopCart = shopCart;
        this.mItemCount = mMenuList.size();
        for (ProductListEntity menu : mMenuList) {
            mItemCount += menu.getProductEntities().size();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MENU_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_right_item_title, parent, false);
            MenuViewHolder menuViewHolder = new MenuViewHolder(view);
            return menuViewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_life_product_bf, parent, false);
            DishViewHolder dishViewHolder = new DishViewHolder(view);
            return dishViewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int sum = 0;
        for (ProductListEntity menu : data) {
            if (position == sum) {
                return MENU_TYPE;
            }
            sum += menu.getProductEntities().size() + 1;
        }
        return DISH_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == MENU_TYPE) {
            MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
            if (menuViewHolder != null) {
                menuViewHolder.right_menu_title.setText(getMenuByPosition(position).getTypeName());
                menuViewHolder.right_menu_layout.setContentDescription(position + "");
            }
        } else {
            final DishViewHolder dishViewHolder = (DishViewHolder) holder;
            if (dishViewHolder != null) {
                //根据下表取商品，当前这个position是右侧（所有展示数据）的下标
                final ProductListEntity.ProductEntity dish = getDishByPosition(position);
                //根据当前下标获取父id
                int parentPosition = getDishPositionByOnePosition(position);
                dishViewHolder.tv_item_life_product_name.setText(dish.getProductName());
                dishViewHolder.tv_item_life_product_monty.setText("月售 " + dish.getProductMonth());
                dishViewHolder.tv_item_life_product_money.setText("" + dish.getProductMoney());
                dishViewHolder.right_dish_layout.setContentDescription(position + "");
                dishViewHolder.tv_group_list_item_count_num.setText(dish.getProductCount() + "");

                int count = 0;
                if (shopCart.getShoppingSingle().containsKey(dish)) {
                    count = shopCart.getShoppingSingle().get(dish);
                }
                if (count <= 0) {
                    dishViewHolder.tv_group_list_item_count_num.setVisibility(View.INVISIBLE);
                    dishViewHolder.iv_group_list_item_count_reduce.setVisibility(View.INVISIBLE);
                } else {
                    dishViewHolder.tv_group_list_item_count_num.setVisibility(View.VISIBLE);
                    dishViewHolder.iv_group_list_item_count_reduce.setVisibility(View.VISIBLE);
                    dishViewHolder.tv_group_list_item_count_num.setText(count + "");
                }
                //加减点击时间
                dishViewHolder.iv_group_list_item_count_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("TAG", "-------------------parentPosition:" + parentPosition);
                        if (shopCart.addShoppingSingle(dish)) {
                            //当前数字变化刷新
                            notifyDataSetChanged();
                            if (shopCartImp != null) {
                                shopCartImp.add(view, position, dish);
                            }
                        }
                    }
                });
                dishViewHolder.iv_group_list_item_count_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //当前数字变化刷新
                        notifyDataSetChanged();
                        if (shopCartImp != null) {
                            shopCartImp.remove(view, position, dish);
                        }
                    }
                });
            }
        }
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    /**
     * 根据父下标获取当前具体商品的下标
     *
     * @param position 父下标
     * @return
     */
    private int getDishPositionByOnePosition(int position) {
        for (ProductListEntity menu : data) {
            if (position > 0 && position <= menu.getProductEntities().size()) {
                return position - 1;
            } else {
                position -= menu.getProductEntities().size() + 1;
            }
        }
        return 0;
    }

    private ProductListEntity getMenuByPosition(int position) {
        int sum = 0;
        for (ProductListEntity menu : data) {
            if (position == sum) {
                return menu;
            }
            sum += menu.getProductEntities().size() + 1;
        }
        return null;
    }

    private ProductListEntity.ProductEntity getDishByPosition(int position) {
        for (ProductListEntity menu : data) {
            if (position > 0 && position <= menu.getProductEntities().size()) {
                return menu.getProductEntities().get(position - 1);
            } else {
                position -= menu.getProductEntities().size() + 1;
            }
        }
        return null;
    }

    public ProductListEntity getMenuOfMenuByPosition(int position) {
        for (ProductListEntity menu : data) {
            if (position == 0) return menu;
            if (position > 0 && position <= menu.getProductEntities().size()) {
                return menu;
            } else {
                position -= menu.getProductEntities().size() + 1;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout right_menu_layout;
        private TextView right_menu_title;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            right_menu_layout = (LinearLayout) itemView.findViewById(R.id.right_menu_item);
            right_menu_title = (TextView) itemView.findViewById(R.id.right_menu_tv);
        }
    }

    private class DishViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item_life_product_name;
        private TextView tv_item_life_product_monty;
        private TextView tv_item_life_product_money;
        private LinearLayout right_dish_layout;

        private ImageView iv_group_list_item_count_reduce;
        private TextView tv_group_list_item_count_num;
        private ImageView iv_group_list_item_count_add;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_life_product_name = (TextView) itemView.findViewById(R.id.tv_item_life_product_name);
            tv_item_life_product_monty = (TextView) itemView.findViewById(R.id.tv_item_life_product_monty);
            tv_item_life_product_money = (TextView) itemView.findViewById(R.id.tv_item_life_product_money);

            right_dish_layout = (LinearLayout) itemView.findViewById(R.id.right_dish_item);

            iv_group_list_item_count_reduce = (ImageView) itemView.findViewById(R.id.iv_group_list_item_count_reduce);
            tv_group_list_item_count_num = (TextView) itemView.findViewById(R.id.tv_group_list_item_count_num);
            iv_group_list_item_count_add = (ImageView) itemView.findViewById(R.id.iv_group_list_item_count_add);

        }
    }
}
