package fun.wraq.process.system.element.piece;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fun.wraq.process.system.element.piece.ElementPieceData.*;

public class ElementPieceRecipe {
    public ItemStack product;
    public List<ItemStack> material;
    public ElementPieceRecipe(ItemStack product, List<ItemStack> material) {
        this.product = product;
        this.material = material;
    }

    private final static List<ElementPieceRecipe> recipes = new ArrayList<>();
    public static List<ElementPieceRecipe> getRecipes() {
        if (recipes.isEmpty()) {
            // 七元素
            Element.getPiece0ItemMap().values().forEach(item -> {
                recipes.add(new ElementPieceRecipe(new ItemStack(item), List.of(
                        new ItemStack(ElementItems.weakPiece0.get(), 64),
                        new ItemStack(item)
                )));
                recipes.add(new ElementPieceRecipe(new ItemStack(ElementItems.weakPiece0.get(), 64), List.of(
                        new ItemStack(ModItems.RainbowPowder.get()),
                        new ItemStack(item, 64)
                )));
            });
            Element.getPiece1ItemMap().values().forEach(item -> {
                Item piece0 = Element.getPiece0ItemMap().get(Element.getPiece1ToElementMap().get(item));
                recipes.add(new ElementPieceRecipe(new ItemStack(item), List.of(
                        new ItemStack(ModItems.RainbowPowder.get()),
                        new ItemStack(piece0, 64)
                )));
            });
            Element.getPiece2ItemMap().values().forEach(item -> {
                Item piece1 = Element.getPiece1ItemMap().get(Element.getPiece2ToElementMap().get(item));
                recipes.add(new ElementPieceRecipe(new ItemStack(item), List.of(
                        new ItemStack(ModItems.RainbowCrystal.get()),
                        new ItemStack(piece1, 64)
                )));
            });
            // 七色棱镜
            List<ItemStack> piece0_7List = new ArrayList<>();
            Element.getPiece0Items().forEach(item -> {
                piece0_7List.add(new ItemStack(item, 7));
            });
            recipes.add(new ElementPieceRecipe(new ItemStack(ModItems.RainbowPowder.get()), piece0_7List));
            recipes.add(new ElementPieceRecipe(new ItemStack(ModItems.RainbowCrystal.get()), List.of(
                    new ItemStack(ModItems.RainbowPowder.get(), 49),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 7)
            )));
        }
        return recipes;
    }

    public static void tryToCompose(Player player, int index0, int index1) {
        Item rainbowPowder = ModItems.RainbowPowder.get();
        Item rainbowCrystal = ModItems.RainbowCrystal.get();
        Item weakPiece0 = ElementItems.weakPiece0.get();
        if (index0 < 7) {
            String elementType = Element.elementList.get(index0);
            Item piece0Item = Element.getPiece0Items().get(index0);
            Item piece1Item = Element.getPiece1ItemMap().get(elementType);
            Item piece2Item = Element.getPiece2ItemMap().get(elementType);
            if (index1 == 0) {
                boolean hasWeakPiece0 = getCount(player, weakPiece0) >= 64;
                boolean hasPiece0 = getCount(player, piece0Item) >= 1;
                if (hasWeakPiece0 && hasPiece0) {
                    reduceCount(player, weakPiece0, 64);
                    reduceCount(player, piece0Item, 1);
                    addCount(player, piece0Item, 64);
                    sendMSGOnComposeSuccessfully(player, piece0Item);
                } else {
                    sendMSGOnLossMaterial(player);
                }
            } else if (index1 == 1) {
                boolean hasRainbowPowder = getCount(player, rainbowPowder) >= 1;
                boolean hasPiece0 = getCount(player, piece0Item) >= 64;
                if (hasRainbowPowder && hasPiece0) {
                    reduceCount(player, rainbowPowder, 1);
                    reduceCount(player, piece0Item, 64);
                    addCount(player, piece1Item, 1);
                    sendMSGOnComposeSuccessfully(player, piece1Item);
                } else {
                    sendMSGOnLossMaterial(player);
                }
            } else if (index1 == 2) {
                boolean hasRainbowCrystal = getCount(player, rainbowCrystal) >= 1;
                boolean hasPiece1 = getCount(player, piece1Item) >= 64;
                if (hasRainbowCrystal && hasPiece1) {
                    reduceCount(player, rainbowCrystal, 1);
                    reduceCount(player, piece1Item, 64);
                    addCount(player, piece2Item, 1);
                    sendMSGOnComposeSuccessfully(player, piece1Item);
                } else {
                    sendMSGOnLossMaterial(player);
                }
            } else if (index1 == 3) {
                boolean hasRainbowPowder = getCount(player, rainbowPowder) >= 1;
                boolean hasPiece0 = getCount(player, piece0Item) >= 64;
                if (hasRainbowPowder && hasPiece0) {
                    reduceCount(player, rainbowPowder, 1);
                    reduceCount(player, piece0Item, 64);
                    addCount(player, weakPiece0, 64);
                    sendMSGOnComposeSuccessfully(player, weakPiece0);
                } else {
                    sendMSGOnLossMaterial(player);
                }
            }
        } else if (index0 == 7) {
            if (index1 == 0) {
                boolean hasPiece0 = true;
                for (Item piece0Item : Element.getPiece0Items()) {
                    if (getCount(player, piece0Item) < 7) {
                        hasPiece0 = false;
                    }
                }
                if (hasPiece0) {
                    for (Item piece0Item : Element.getPiece0Items()) {
                        reduceCount(player, piece0Item, 7);
                    }
                    addCount(player, rainbowPowder, 1);
                    sendMSGOnComposeSuccessfully(player, rainbowPowder);
                } else {
                    sendMSGOnLossMaterial(player);
                }
            } else {
                boolean hasRainbowPowder = getCount(player, rainbowPowder) >= 49;
                boolean hasCompleteGem = InventoryOperation
                        .checkPlayerHasItem(player, ModItems.COMPLETE_GEM.get(), 7);
                if (hasRainbowPowder && hasCompleteGem) {
                    reduceCount(player, rainbowPowder, 49);
                    InventoryOperation.removeItem(player, ModItems.COMPLETE_GEM.get(), 7);
                    addCount(player, rainbowCrystal, 1);
                    sendMSGOnComposeSuccessfully(player, rainbowCrystal);
                } else {
                    sendMSGOnLossMaterial(player);
                }
            }
        } else {
            sendMSG(player, Te.s("暂时没有这个配方"));
        }
    }

    public static void tryToGet(Player player, int index0, int index1) {
        List<Item> items = getItems(index0);
        if (index0 < 7) {
            if (index1 >= items.size()) {
                sendMSG(player, Te.s("没有这个物品"));
                return;
            } else {
                Item item = items.get(index1);
                if (index1 < 3) {
                    tryToSendItemToPlayer(player, item);
                } else {
                    tryToSendItemToPlayer(player, item, 64);
                }
            }
        } else if (index0 == 7) {
            if (index1 > 1) {
                sendMSG(player, Te.s("没有这个物品"));
                return;
            }
            Item item = items.get(index1);
            tryToSendItemToPlayer(player, item);
        } else {
            if (index1 > 0) {
                sendMSG(player, Te.s("没有这个物品"));
                return;
            }
            Item item = items.get(index1);
            tryToSendItemToPlayer(player, item);
        }
    }

    public static void tryToSendItemToPlayer(Player player, Item item, int count) {
        if (getCount(player, item) >= count) {
            reduceCount(player, item, count);
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(item, count));
            MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        } else {
            sendMSGOnLossElement(player);
        }
    }

    public static void tryToSendItemToPlayer(Player player, Item item) {
        tryToSendItemToPlayer(player, item, 1);
    }

    public static List<Item> getItems(int index) {
        if (index < 7) {
            String element = Element.elementList.get(index);
            return List.of(
                    Element.getPiece0ItemMap().get(element),
                    Element.getPiece1ItemMap().get(element),
                    Element.getPiece2ItemMap().get(element),
                    ElementItems.weakPiece0.get()
            );
        } else {
            if (index == 7) {
                return List.of(
                        ModItems.RainbowPowder.get(),
                        ModItems.RainbowCrystal.get()
                );
            } else {
                return List.of(
                        ElementItems.weakPiece0.get()
                );
            }
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("量子元素", CustomStyle.styleOfWorld), content);
    }

    public static void sendMSGOnComposeSuccessfully(Player player, Item item) {
        sendMSG(player, Te.s(item, "合成成功"));
        MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
    }

    public static void sendMSGOnLossMaterial(Player player) {
        sendMSG(player, Te.s("所需材料不足"));
        MySound.soundToPlayer(player, SoundEvents.COMPOSTER_READY);
    }

    public static void sendMSGOnLossElement(Player player) {
        sendMSG(player, Te.s("量子化材料不足", CustomStyle.styleOfWorld));
        MySound.soundToPlayer(player, SoundEvents.COMPOSTER_READY);
    }

    public static Set<Item> itemSet = new HashSet<>();
    public static Set<Item> getItemSet() {
        if (itemSet.isEmpty()) {
            for (int i = 0 ; i < 9 ; i ++) {
                itemSet.addAll(getItems(i));
            }
        }
        return itemSet;
    }
}
