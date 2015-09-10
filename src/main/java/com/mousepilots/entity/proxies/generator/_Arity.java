/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.entity.proxies.generator;

import java.util.EnumSet;

    public enum _Arity {
        OneToOne,OneToMany,ManyToOne,ManyToMany;
        
        /**
         * @return {@code this}' relational getInverse such that {@code xToY.getInverse()==yToX}.
         */
        public _Arity getInverse(){
            switch(this){
                case OneToOne : return this;
                case OneToMany : return ManyToOne;
                case ManyToOne : return OneToMany;
                case ManyToMany : return this;
                default: throw new IllegalStateException("unknown Type " + this);
            }
        }

        /** contains the types corresponding from collection valued properties */
        private static final EnumSet<_Arity> FROM_ONES = EnumSet.of(OneToOne, OneToMany);

        /** contains the types corresponding from collection valued properties */
        private static final EnumSet<_Arity> FROM_MANIES = EnumSet.of(ManyToOne, ManyToMany);
        
        
        /** contains the types corresponding to collection valued properties */
        private static final EnumSet<_Arity> TO_ONES = EnumSet.of(OneToOne, ManyToOne);

        /** contains the types corresponding to collection valued properties */
        private static final EnumSet<_Arity> TO_MANIES = EnumSet.of(OneToMany, ManyToMany);
        
        public boolean isFromOne(){
            return FROM_ONES.contains(this);
        }
        
        public boolean isFromMany(){
            return FROM_MANIES.contains(this);
        }

        public boolean isToOne(){
            return TO_ONES.contains(this);
        }
        
        public boolean isToMany(){
            return TO_MANIES.contains(this);
        }
    }
