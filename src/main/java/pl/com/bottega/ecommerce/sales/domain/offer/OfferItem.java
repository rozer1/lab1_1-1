/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    private Product product;

    private Discount discount;

    private Money totalCost;

    private int quantity;

    public OfferItem(Product product, int quantity, Money totalCost) {
        this(product, quantity,null, totalCost);
    }

    public OfferItem(Product product, int quantity, Discount discount,Money totalCost ) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
        this.totalCost = totalCost;
        //this.discountCause = discountCause;


        this.totalCost.setValue(product.getPrice().multiply(new BigDecimal(quantity)).subtract(discount.getValue().getValue()));
    }

    public Product getProduct() {
        return product;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, discount, quantity, totalCost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OfferItem other = (OfferItem) obj;
        return Objects.equals(product, other.getProduct()) && Objects.equals(discount, other.discount) && Objects.equals(totalCost,
                other.getTotalCost()) && quantity == other.quantity;
    }

    /**
     *
     * @param other
     * @param delta acceptable percentage difference
     * @return returns true if product offers are the same and false if product offers are different
     */
    public boolean sameAs(OfferItem other, double delta) {
        if(product == null){
            if(other.product != null){
                return false;
            }
        } else if (!product.sameAs(other.product)){
            return false;
        }

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getValue().compareTo(other.totalCost.getValue()) > 0) {
            max = totalCost.getValue();
            min = other.totalCost.getValue();
        } else {
            max = other.totalCost.getValue();
            min = totalCost.getValue();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

}
